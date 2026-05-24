package com.rbdarts.core.domain

import java.time.Instant
import java.util.UUID
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sqrt

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
    const val BullseyeTarget: Int = 25

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
        return targetForInning(inningNumber, BaseballRuleSet())
    }

    fun targetForInning(inningNumber: Int, ruleSet: BaseballRuleSet): Int {
        validateInning(inningNumber)
        if (inningNumber <= 20) return inningNumber
        return when (ruleSet.post20TieRule) {
            Post20TieRule.Repeat20 -> 20
            Post20TieRule.RestartAt1 -> ((inningNumber - 1) % 20) + 1
            Post20TieRule.BullseyeTiebreaker -> BullseyeTarget
        }
    }

    fun maximumTurnScore(ruleSet: BaseballRuleSet = BaseballRuleSet()): Int =
        ruleSet.dartsPerTurn * ruleSet.tripleValue

    fun maximumIndividualScore(ruleSet: BaseballRuleSet = BaseballRuleSet()): Int =
        ruleSet.regulationInnings * maximumTurnScore(ruleSet)

    fun maximumTeamScore(playerCount: Int, ruleSet: BaseballRuleSet = BaseballRuleSet()): Int {
        if (playerCount < 0) {
            throw ScoreValidationException("Player count cannot be negative.")
        }
        return playerCount * maximumIndividualScore(ruleSet)
    }

    fun validateRuleSet(ruleSet: BaseballRuleSet) {
        if (ruleSet.regulationInnings < 1) {
            throw ScoreValidationException("Regulation innings must be at least 1.")
        }
        if (ruleSet.dartsPerTurn != 3) {
            throw ScoreValidationException("Standard Baseball Darts requires exactly 3 darts per turn.")
        }
        if (maximumTurnScore(ruleSet) > 9) {
            throw ScoreValidationException("Standard Baseball Darts turn scores cannot exceed 9.")
        }
    }

    fun scoreDart(dart: BaseballDartThrow, inningTarget: Int = dart.targetNumber, ruleSet: BaseballRuleSet = BaseballRuleSet()): Int {
        validateRuleSet(ruleSet)
        if (dart.dartIndex !in 1..ruleSet.dartsPerTurn) {
            throw ScoreValidationException("Dart index must be between 1 and ${ruleSet.dartsPerTurn}.")
        }
        if (dart.validity != DartValidity.Valid || dart.ring == DartRing.Miss) return 0

        if (inningTarget == BullseyeTarget) {
            return when (dart.ring) {
                DartRing.OuterBull -> 1
                DartRing.InnerBull -> 2
                else -> 0
            }
        }

        if (dart.landedNumber != inningTarget) return 0
        return when (dart.ring) {
            DartRing.Single -> ruleSet.singleValue
            DartRing.Double -> ruleSet.doubleValue
            DartRing.Triple -> ruleSet.tripleValue
            DartRing.OuterBull, DartRing.InnerBull -> {
                if (ruleSet.bullseyeBonusEnabled) {
                    if (dart.ring == DartRing.OuterBull) 1 else 2
                } else {
                    0
                }
            }
            DartRing.Miss -> 0
        }
    }

    fun scoreTurn(turn: BaseballTurn, ruleSet: BaseballRuleSet = BaseballRuleSet()): Int {
        validateRuleSet(ruleSet)
        validateInning(turn.inningNumber)
        if (turn.darts.size != ruleSet.dartsPerTurn) {
            throw ScoreValidationException("A complete Baseball Darts turn requires ${ruleSet.dartsPerTurn} darts.")
        }
        val expectedTarget = targetForInning(turn.inningNumber, ruleSet)
        if (turn.targetNumber != expectedTarget) {
            throw ScoreValidationException("Turn target ${turn.targetNumber} does not match inning target $expectedTarget.")
        }
        val total = turn.darts.sumOf { scoreDart(it, expectedTarget, ruleSet) }
        validateScore(total)
        return total
    }

    fun participantInningTotals(
        turns: List<BaseballTurn>,
        participantId: String,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): Map<Int, Int> =
        turns.filter { it.participantId == participantId }
            .groupBy { it.inningNumber }
            .mapValues { (_, inningTurns) -> inningTurns.sumOf { scoreTurn(it, ruleSet) } }

    fun participantTotal(
        turns: List<BaseballTurn>,
        participantId: String,
        throughInnings: Int? = null,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): Int =
        participantInningTotals(turns, participantId, ruleSet)
            .filterKeys { throughInnings == null || it <= throughInnings }
            .values
            .sum()

    fun teamInningTotal(
        turns: List<BaseballTurn>,
        teamId: String,
        inningNumber: Int,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): Int {
        validateInning(inningNumber)
        return turns.filter { it.teamId == teamId && it.inningNumber == inningNumber }
            .sumOf { scoreTurn(it, ruleSet) }
    }

    fun validateBaseballParticipants(participants: List<BaseballParticipant>) {
        if (participants.size < 2) {
            throw ScoreValidationException("A Baseball Darts game requires at least two participants.")
        }
        participants.forEach { participant ->
            if (participant.kind == BaseballParticipantKind.Team) {
                if (participant.playerIds.size < 2) {
                    throw ScoreValidationException("Official team Baseball Darts requires at least two players per team.")
                }
                if (participant.lineupOrder.isNotEmpty() && participant.lineupOrder.toSet() != participant.playerIds.toSet()) {
                    throw ScoreValidationException("Team batting order must include the same players as the team roster.")
                }
            }
        }
    }

    fun isParticipantCompleteForInning(
        participant: BaseballParticipant,
        turns: List<BaseballTurn>,
        inningNumber: Int,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): Boolean {
        val participantTurns = turns.filter { it.participantId == participant.participantId && it.inningNumber == inningNumber }
        return if (participant.kind == BaseballParticipantKind.Team) {
            val expectedPlayers = participant.lineupOrder.ifEmpty { participant.playerIds }
            expectedPlayers.isNotEmpty() &&
                expectedPlayers.all { playerId -> participantTurns.any { it.playerId == playerId && it.darts.size == ruleSet.dartsPerTurn } }
        } else {
            participantTurns.any { it.darts.size == ruleSet.dartsPerTurn }
        }
    }

    fun lastEqualTurnInning(
        participants: List<BaseballParticipant>,
        turns: List<BaseballTurn>,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): Int {
        validateBaseballParticipants(participants)
        validateRuleSet(ruleSet)
        val maxInning = turns.maxOfOrNull { it.inningNumber } ?: 0
        var lastComplete = 0
        for (inning in 1..maxInning) {
            if (participants.all { isParticipantCompleteForInning(it, turns, inning, ruleSet) }) {
                lastComplete = inning
            } else {
                break
            }
        }
        return lastComplete
    }

    fun summarizeBaseballGame(
        participants: List<BaseballParticipant>,
        turns: List<BaseballTurn>,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): BaseballGameSummary {
        validateBaseballParticipants(participants)
        validateRuleSet(ruleSet)
        turns.forEach { scoreTurn(it, ruleSet) }

        val equalTurnInning = lastEqualTurnInning(participants, turns, ruleSet)
        val scoringThrough = if (equalTurnInning == 0) turns.maxOfOrNull { it.inningNumber } ?: 0 else equalTurnInning
        val summaries = participants.map { participant ->
            val inningTotals = participantInningTotals(turns, participant.participantId, ruleSet)
                .filterKeys { it <= scoringThrough }
            BaseballParticipantSummary(
                participantId = participant.participantId,
                displayName = participant.displayName,
                total = participant.startingHandicap + inningTotals.values.sum(),
                inningTotals = inningTotals,
                zeroCount = inningTotals.values.count { it == 0 },
                nineCount = inningTotals.values.count { it == maximumTurnScore(ruleSet) }
            )
        }.sortedBy { it.displayName }

        val highestTotal = summaries.maxOfOrNull { it.total } ?: 0
        val leaders = summaries.filter { it.total == highestTotal }
        val orderedTotals = summaries.map { it.total }.sortedDescending()
        val tied = leaders.size > 1
        val equalTurnComplete = equalTurnInning >= ruleSet.regulationInnings
        val canDeclareWinner = equalTurnComplete && !tied
        val needsExtra = ruleSet.extraInningsEnabled && equalTurnComplete && tied
        val nextInning = when {
            needsExtra -> equalTurnInning + 1
            equalTurnInning == 0 -> 1
            else -> equalTurnInning
        }

        return BaseballGameSummary(
            participantSummaries = summaries,
            winnerIds = if (canDeclareWinner) leaders.map { it.participantId } else emptyList(),
            margin = if (canDeclareWinner && orderedTotals.size > 1) orderedTotals[0] - orderedTotals[1] else 0,
            inningsPlayed = maxOf(equalTurnInning, ruleSet.regulationInnings),
            needsExtraInning = needsExtra,
            currentTarget = targetForInning(nextInning, ruleSet)
        )
    }

    fun canEditLockedGame(role: LeagueRole): Boolean =
        role == LeagueRole.LeagueManager || role == LeagueRole.Admin

    fun canCorrectScore(gameStatus: GameStatus, role: LeagueRole, isOwnTeam: Boolean = false, policyAllowsScorekeeper: Boolean = true): Boolean =
        when (gameStatus) {
            GameStatus.Locked, GameStatus.Finalized -> canEditLockedGame(role)
            GameStatus.Complete, GameStatus.Corrected -> role == LeagueRole.LeagueManager ||
                role == LeagueRole.Admin ||
                (role == LeagueRole.TeamManager && isOwnTeam)
            GameStatus.Scoring -> role == LeagueRole.LeagueManager ||
                role == LeagueRole.Admin ||
                role == LeagueRole.Scorekeeper && policyAllowsScorekeeper ||
                role == LeagueRole.TeamManager && isOwnTeam
            GameStatus.Setup -> role == LeagueRole.LeagueManager || role == LeagueRole.Admin || role == LeagueRole.TeamManager
            GameStatus.Voided -> false
        }

    fun applyAuthorizedCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        editedByUserId: String,
        editedByRole: LeagueRole,
        affectedRecords: List<String>,
        gameStatus: GameStatus,
        isOwnTeam: Boolean = false,
        now: Instant = Instant.now()
    ): Pair<InningScore, CorrectionAuditRecord> {
        if (!canCorrectScore(gameStatus, editedByRole, isOwnTeam)) {
            throw ScoreValidationException("User role is not authorized to correct this score.")
        }
        return applyCorrection(score, newValue, reason, editedByUserId, editedByRole, affectedRecords, now)
    }

    fun canSubstitutePlayer(
        role: LeagueRole,
        midGame: Boolean,
        isOwnTeam: Boolean = false,
        leagueAllowsMidGame: Boolean = false
    ): Boolean {
        val roleAllowed = role == LeagueRole.LeagueManager ||
            role == LeagueRole.Admin ||
            (role == LeagueRole.TeamManager && isOwnTeam)
        return roleAllowed && (!midGame || leagueAllowsMidGame)
    }

    fun scoreboardSnapshot(
        gameId: String,
        participants: List<BaseballParticipant>,
        turns: List<BaseballTurn>,
        currentParticipantId: String,
        currentThrowerDisplayName: String,
        currentInning: Int,
        currentTurnDarts: List<BaseballDartThrow> = emptyList(),
        gameStatus: GameStatus = GameStatus.Scoring,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): BaseballScoreboardSnapshot {
        val summary = summarizeBaseballGame(participants, turns, ruleSet)
        val currentParticipant = participants.firstOrNull { it.participantId == currentParticipantId }
        val target = targetForInning(currentInning, ruleSet)
        val currentTurnScore = currentTurnDarts.sumOf { scoreDart(it, target, ruleSet) }
        val participantTotal = participantTotal(turns, currentParticipantId, currentInning, ruleSet) + currentTurnScore
        val teamInning = currentParticipant?.teamId?.let { teamInningTotal(turns, it, currentInning, ruleSet) + currentTurnScore }
        val teamTotal = currentParticipant?.teamId?.let { teamId ->
            turns.filter { it.teamId == teamId && it.inningNumber <= currentInning }.sumOf { scoreTurn(it, ruleSet) } + currentTurnScore
        }
        val liveTotals = participants.associate { participant ->
            val base = participantTotal(turns, participant.participantId, currentInning, ruleSet) + participant.startingHandicap
            val liveTotal = if (participant.participantId == currentParticipantId) base + currentTurnScore else base
            participant.participantId to liveTotal
        }
        val highestLiveTotal = liveTotals.values.maxOrNull()
        val liveLeaders = liveTotals.filterValues { it == highestLiveTotal }.keys
        val orderedLiveTotals = liveTotals.values.sortedDescending()

        return BaseballScoreboardSnapshot(
            gameId = gameId,
            currentInning = currentInning,
            targetNumber = target,
            currentThrowerId = currentParticipantId,
            currentThrowerDisplayName = currentThrowerDisplayName,
            playerInningScore = currentTurnScore,
            playerTotal = participantTotal,
            teamInningScore = teamInning,
            teamTotal = teamTotal,
            leaderId = if (liveLeaders.size == 1) liveLeaders.first() else null,
            leadMargin = if (liveLeaders.size == 1 && orderedLiveTotals.size > 1) orderedLiveTotals[0] - orderedLiveTotals[1] else 0,
            remainingInnings = (ruleSet.regulationInnings - currentInning).coerceAtLeast(0),
            needsExtraInning = summary.needsExtraInning,
            gameStatus = gameStatus
        )
    }

    fun baseballAnalytics(
        subjectId: String,
        subjectType: BaseballAnalyticsSubject,
        turns: List<BaseballTurn>,
        wins: Int = 0,
        gamesPlayed: Int = 0,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ): BaseballAnalyticsSnapshot {
        val subjectTurns = turns.filter {
            if (subjectType == BaseballAnalyticsSubject.Team) it.teamId == subjectId || it.participantId == subjectId else it.playerId == subjectId || it.participantId == subjectId
        }
        if (subjectTurns.isEmpty()) {
            return BaseballAnalyticsSnapshot(subjectId, subjectType, null, null, 0, null, null, null, null, null, emptyList(), emptyList(), false)
        }
        val scores = subjectTurns.map { scoreTurn(it, ruleSet) }
        val average = scores.average()
        val variance = scores.map { (it - average) * (it - average) }.average()
        val darts = subjectTurns.flatMap { it.darts }
        val validTripleCount = darts.count { it.validity == DartValidity.Valid && it.ring == DartRing.Triple && it.landedNumber == it.targetNumber }
        val scoredDartCount = darts.count { it.validity == DartValidity.Valid }
        val byTarget = subjectTurns.groupBy { it.targetNumber }.mapValues { (_, targetTurns) -> targetTurns.map { scoreTurn(it, ruleSet) }.average() }
        val best = byTarget.entries.sortedWith(compareByDescending<Map.Entry<Int, Double>> { it.value }.thenBy { it.key }).take(3).map { it.key }
        val weakest = byTarget.entries.sortedWith(compareBy<Map.Entry<Int, Double>> { it.value }.thenBy { it.key }).take(3).map { it.key }

        return BaseballAnalyticsSnapshot(
            subjectId = subjectId,
            subjectType = subjectType,
            averageScorePerInning = average,
            highestInningScore = scores.maxOrNull(),
            shutoutInnings = scores.count { it == 0 },
            tripleRate = if (scoredDartCount == 0) null else validTripleCount.toDouble() / scoredDartCount,
            winPercentage = if (gamesPlayed <= 0) null else wins.toDouble() / gamesPlayed,
            projectedFinalScore = if (subjectTurns.isEmpty()) null else average * ruleSet.regulationInnings,
            comebackProbability = null,
            consistency = sqrt(variance),
            bestTargetNumbers = best,
            weakestTargetNumbers = weakest,
            isEstimate = true
        )
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
