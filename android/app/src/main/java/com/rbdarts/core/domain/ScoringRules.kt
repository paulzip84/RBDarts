package com.rbdarts.core.domain

import java.time.Instant
import java.util.UUID
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

class ScoreValidationException(message: String) : IllegalArgumentException(message)

data class ParticipantScorecard(
    val participantId: String,
    val displayName: String,
    val scoresByInning: Map<Int, Int> = emptyMap(),
    val average: Double? = null
)

data class ParticipantSummary(
    val participantId: String,
    val displayName: String,
    val total: Int,
    val zeroCount: Int,
    val nineCount: Int,
    val performanceVersusAverage: Double?
)

data class GameSummary(
    val participantSummaries: List<ParticipantSummary>,
    val winnerIds: List<String>,
    val margin: Int,
    val inningsPlayed: Int,
    val needsExtraInning: Boolean
)

data class HandicapResult(
    val lowerAverageTeamId: String?,
    val higherAverageTeamId: String?,
    val handicap: Double
)

data class PracticeStats(
    val attempts: Int,
    val average: Double,
    val bestScore: Int,
    val zeroCount: Int,
    val nineCount: Int,
    val recentTrend: Double
)

data class TeamStanding(
    val teamId: String,
    val leaguePoints: Double,
    val gamesWon: Int,
    val pointDifferential: Int,
    val totalAdjustedScore: Double,
    val totalRawScore: Int
)

object ScoringRules {
    const val DefaultRegulationInnings: Int = 9

    fun validateScore(score: Int) {
        if (score !in 0..9) {
            throw ScoreValidationException("Scores must be whole numbers from 0 through 9.")
        }
    }

    fun validateInning(inningNumber: Int) {
        if (inningNumber <= 0) {
            throw ScoreValidationException("Inning numbers must be positive.")
        }
    }

    fun targetForInning(inningNumber: Int): Int {
        validateInning(inningNumber)
        return inningNumber
    }

    fun recordScore(scorecard: ParticipantScorecard, inningNumber: Int, score: Int): ParticipantScorecard {
        validateScore(score)
        validateInning(inningNumber)
        return scorecard.copy(scoresByInning = scorecard.scoresByInning + (inningNumber to score))
    }

    fun participantTotal(scorecard: ParticipantScorecard, throughInnings: Int? = null): Int =
        scorecard.scoresByInning.entries
            .filter { throughInnings == null || it.key <= throughInnings }
            .sumOf { it.value }

    fun summarizeStandalone(
        participants: List<ParticipantScorecard>,
        regulationInnings: Int = DefaultRegulationInnings,
        extraInningsEnabled: Boolean = true
    ): GameSummary {
        val maxEnteredInning = participants.flatMap { it.scoresByInning.keys }.maxOrNull() ?: regulationInnings
        val inningsPlayed = maxOf(regulationInnings, maxEnteredInning)
        val summaries = participants.map { participant ->
            val enteredScores = participant.scoresByInning.filterKeys { it <= inningsPlayed }.values
            val total = participantTotal(participant, inningsPlayed)
            ParticipantSummary(
                participantId = participant.participantId,
                displayName = participant.displayName,
                total = total,
                zeroCount = enteredScores.count { it == 0 },
                nineCount = enteredScores.count { it == 9 },
                performanceVersusAverage = participant.average?.let { total - it }
            )
        }.sortedBy { it.displayName }

        val highestTotal = summaries.maxOfOrNull { it.total } ?: 0
        val leaders = summaries.filter { it.total == highestTotal }.map { it.participantId }
        val orderedTotals = summaries.map { it.total }.sortedDescending()
        val tied = leaders.size > 1
        val margin = if (!tied && orderedTotals.size > 1) orderedTotals[0] - orderedTotals[1] else 0

        return GameSummary(
            participantSummaries = summaries,
            winnerIds = if (tied) emptyList() else leaders,
            margin = margin,
            inningsPlayed = inningsPlayed,
            needsExtraInning = inningsPlayed >= regulationInnings && tied && extraInningsEnabled
        )
    }

    fun handicap(
        teamAverageSums: Map<String, Double>,
        handicapPercent: Double,
        roundingRule: HandicapRoundingRule
    ): HandicapResult {
        if (teamAverageSums.size != 2) return HandicapResult(null, null, 0.0)
        val ordered = teamAverageSums.entries.sortedWith(compareBy<Map.Entry<String, Double>> { it.value }.thenBy { it.key })
        val lower = ordered.first()
        val higher = ordered.last()
        if (lower.value >= higher.value) return HandicapResult(null, null, 0.0)

        val raw = (higher.value - lower.value) * (handicapPercent / 100.0)
        val rounded = when (roundingRule) {
            HandicapRoundingRule.Nearest -> round(raw)
            HandicapRoundingRule.Floor -> floor(raw)
            HandicapRoundingRule.Ceiling -> ceil(raw)
            HandicapRoundingRule.Decimal -> raw
        }
        return HandicapResult(lower.key, higher.key, rounded)
    }

    fun lockableGameIds(games: List<Game>, whenStartingGameNumber: Int): List<String> =
        games.filter { it.gameNumber < whenStartingGameNumber && it.status == GameStatus.Complete }
            .map { it.gameId }

    fun applyCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        editedByUserId: String,
        editedByRole: LeagueRole,
        affectedRecords: List<String>,
        now: Instant = Instant.now()
    ): Pair<InningScore, CorrectionAuditRecord> {
        validateScore(newValue)
        if (reason.isBlank()) {
            throw ScoreValidationException("A correction reason is required.")
        }

        val correctedScore = score.copy(score = newValue, updatedAt = now)
        val audit = CorrectionAuditRecord(
            correctionId = UUID.randomUUID().toString(),
            leagueId = score.teamId ?: "standalone",
            matchId = score.gameId,
            gameId = score.gameId,
            inningScoreId = score.inningScoreId,
            editedByUserId = editedByUserId,
            editedByRole = editedByRole,
            reason = reason,
            previousValue = score.score,
            newValue = newValue,
            affectedRecords = affectedRecords,
            status = CorrectionStatus.Applied,
            createdAt = now,
            appliedAt = now
        )
        return correctedScore to audit
    }

    fun practiceStats(attempts: List<PracticeAttempt>): PracticeStats {
        if (attempts.isEmpty()) {
            return PracticeStats(0, 0.0, 0, 0, 0, 0.0)
        }
        val scores = attempts.map { it.score }
        val average = scores.sum().toDouble() / scores.size
        val recent = scores.takeLast(3)
        val previous = scores.dropLast(minOf(3, scores.size)).takeLast(3)
        val recentAverage = if (recent.isEmpty()) average else recent.average()
        val previousAverage = if (previous.isEmpty()) average else previous.average()
        return PracticeStats(
            attempts = scores.size,
            average = average,
            bestScore = scores.max(),
            zeroCount = scores.count { it == 0 },
            nineCount = scores.count { it == 9 },
            recentTrend = recentAverage - previousAverage
        )
    }

    fun rankedStandings(standings: List<TeamStanding>): List<TeamStanding> =
        standings.sortedWith(
            compareByDescending<TeamStanding> { it.leaguePoints }
                .thenByDescending { it.gamesWon }
                .thenByDescending { it.pointDifferential }
                .thenByDescending { it.totalAdjustedScore }
                .thenByDescending { it.totalRawScore }
                .thenBy { it.teamId }
        )

    fun projectedFinalScore(
        currentScore: Int,
        inningsPlayed: Int,
        regulationInnings: Int = DefaultRegulationInnings
    ): Double = if (inningsPlayed <= 0) 0.0 else (currentScore.toDouble() / inningsPlayed) * regulationInnings
}
