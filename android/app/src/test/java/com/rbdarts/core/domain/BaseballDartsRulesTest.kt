package com.rbdarts.core.domain

import com.google.common.truth.Truth.assertThat
import java.time.Instant
import org.junit.Assert.assertThrows
import org.junit.Test

class BaseballDartsRulesTest {
    private val ruleSet = BaseballRuleSet()

    @Test
    fun dartLevelScoringCountsOnlyCurrentTarget() {
        val turn = turn("turn-1", "game-1", "player-a", null, "player-a", inning = 4, score = 4)

        assertThat(ScoringRules.scoreTurn(turn, ruleSet)).isEqualTo(4)
        assertThat(ScoringRules.scoreDart(dart(1, 4, 4, DartRing.Single), 4, ruleSet)).isEqualTo(1)
        assertThat(ScoringRules.scoreDart(dart(2, 4, 4, DartRing.Triple), 4, ruleSet)).isEqualTo(3)
        assertThat(ScoringRules.scoreDart(dart(3, 4, 5, DartRing.Triple, DartValidity.WrongNumber), 4, ruleSet)).isEqualTo(0)
    }

    @Test
    fun standardMaximumsAndPost20TargetsAreEnforced() {
        val maxTurn = BaseballTurn(
            turnId = "max-turn",
            gameId = "game-1",
            inningNumber = 9,
            targetNumber = 9,
            participantId = "player-a",
            teamId = null,
            playerId = "player-a",
            lineupSlot = 1,
            darts = listOf(
                dart(1, 9, 9, DartRing.Triple),
                dart(2, 9, 9, DartRing.Triple),
                dart(3, 9, 9, DartRing.Triple)
            ),
            enteredByUserId = "scorekeeper",
            createdAt = Instant.EPOCH,
            updatedAt = Instant.EPOCH
        )

        assertThat(ScoringRules.scoreTurn(maxTurn)).isEqualTo(9)
        assertThat(ScoringRules.maximumIndividualScore()).isEqualTo(81)
        assertThat(ScoringRules.maximumTeamScore(4)).isEqualTo(324)
        assertThat(ScoringRules.targetForInning(21, BaseballRuleSet(post20TieRule = Post20TieRule.Repeat20))).isEqualTo(20)
        assertThat(ScoringRules.targetForInning(21, BaseballRuleSet(post20TieRule = Post20TieRule.RestartAt1))).isEqualTo(1)
        assertThat(ScoringRules.targetForInning(21, BaseballRuleSet(post20TieRule = Post20TieRule.BullseyeTiebreaker))).isEqualTo(ScoringRules.BullseyeTarget)
    }

    @Test
    fun standardNineInningGameDeclaresWinnerAfterEqualTurns() {
        val participants = players("player-a" to "Avery", "player-b" to "Blake")
        val turns = (1..9).flatMap { inning ->
            listOf(
                turn("a-$inning", "game-1", "player-a", null, "player-a", inning, 5),
                turn("b-$inning", "game-1", "player-b", null, "player-b", inning, if (inning == 9) 4 else 5)
            )
        }

        val summary = ScoringRules.summarizeBaseballGame(participants, turns)

        assertThat(summary.winnerIds).containsExactly("player-a")
        assertThat(summary.margin).isEqualTo(1)
        assertThat(summary.needsExtraInning).isFalse()
        assertThat(summary.currentTarget).isEqualTo(9)
    }

    @Test
    fun teamScoringAggregatesPlayerTurnsAndValidatesLineup() {
        val team = BaseballParticipant(
            participantId = "team-a",
            displayName = "Team A",
            kind = BaseballParticipantKind.Team,
            teamId = "team-a",
            playerIds = listOf("player-1", "player-2", "player-3"),
            lineupOrder = listOf("player-1", "player-2", "player-3")
        )
        val opponent = BaseballParticipant(
            participantId = "team-b",
            displayName = "Team B",
            kind = BaseballParticipantKind.Team,
            teamId = "team-b",
            playerIds = listOf("player-4", "player-5"),
            lineupOrder = listOf("player-4", "player-5")
        )
        val turns = listOf(
            turn("t1", "game-1", "team-a", "team-a", "player-1", 1, 2),
            turn("t2", "game-1", "team-a", "team-a", "player-2", 1, 1),
            turn("t3", "game-1", "team-a", "team-a", "player-3", 1, 3)
        )

        ScoringRules.validateBaseballParticipants(listOf(team, opponent))

        assertThat(ScoringRules.teamInningTotal(turns, "team-a", 1)).isEqualTo(6)
        assertThat(ScoringRules.isParticipantCompleteForInning(team, turns, 1)).isTrue()
    }

    @Test
    fun extraInningsRequireEqualTurnsBeforeWinner() {
        val participants = players("team-a" to "Team A", "team-b" to "Team B")
        val tiedRegulation = (1..9).flatMap { inning ->
            listOf(
                turn("a-$inning", "game-1", "team-a", null, "team-a", inning, 5),
                turn("b-$inning", "game-1", "team-b", null, "team-b", inning, 5)
            )
        }
        val partialExtra = tiedRegulation + turn("a-10", "game-1", "team-a", null, "team-a", 10, 2)

        val waiting = ScoringRules.summarizeBaseballGame(participants, partialExtra)

        assertThat(waiting.winnerIds).isEmpty()
        assertThat(waiting.needsExtraInning).isTrue()
        assertThat(waiting.currentTarget).isEqualTo(10)

        val resolved = ScoringRules.summarizeBaseballGame(
            participants,
            partialExtra + turn("b-10", "game-1", "team-b", null, "team-b", 10, 3)
        )

        assertThat(resolved.winnerIds).containsExactly("team-b")
        assertThat(resolved.margin).isEqualTo(1)
        assertThat(resolved.needsExtraInning).isFalse()
    }

    @Test
    fun lockedGameCorrectionsRequireAuthorizedRoleAndAuditReason() {
        val score = InningScore("score-1", "game-1", "player-a", "team-a", 7, 7, 2, "scorekeeper", Instant.EPOCH, Instant.EPOCH)

        assertThrows(ScoreValidationException::class.java) {
            ScoringRules.applyAuthorizedCorrection(
                score = score,
                newValue = 3,
                reason = "Obvious entry mistake",
                editedByUserId = "player-user",
                editedByRole = LeagueRole.Player,
                affectedRecords = listOf("score-1"),
                gameStatus = GameStatus.Locked
            )
        }

        val (corrected, audit) = ScoringRules.applyAuthorizedCorrection(
            score = score,
            newValue = 3,
            reason = "Obvious entry mistake",
            editedByUserId = "manager-user",
            editedByRole = LeagueRole.LeagueManager,
            affectedRecords = listOf("score-1"),
            gameStatus = GameStatus.Locked,
            now = Instant.EPOCH
        )

        assertThat(corrected.score).isEqualTo(3)
        assertThat(audit.previousValue).isEqualTo(2)
        assertThat(audit.newValue).isEqualTo(3)
        assertThat(audit.editedByRole).isEqualTo(LeagueRole.LeagueManager)
        assertThat(audit.reason).isEqualTo("Obvious entry mistake")
    }

    @Test
    fun scoreboardAndAnalyticsAreDerivedFromDarts() {
        val participants = players("player-a" to "Avery", "player-b" to "Blake")
        val turns = listOf(
            turn("a-1", "game-1", "player-a", null, "player-a", 1, 9),
            turn("b-1", "game-1", "player-b", null, "player-b", 1, 3),
            turn("a-2", "game-1", "player-a", null, "player-a", 2, 0)
        )

        val scoreboard = ScoringRules.scoreboardSnapshot(
            gameId = "game-1",
            participants = participants,
            turns = turns,
            currentParticipantId = "player-b",
            currentThrowerDisplayName = "Blake",
            currentInning = 2
        )
        val analytics = ScoringRules.baseballAnalytics(
            subjectId = "player-a",
            subjectType = BaseballAnalyticsSubject.Player,
            turns = turns,
            wins = 1,
            gamesPlayed = 2
        )

        assertThat(scoreboard.targetNumber).isEqualTo(2)
        assertThat(scoreboard.leaderId).isEqualTo("player-a")
        assertThat(scoreboard.leadMargin).isEqualTo(6)
        assertThat(scoreboard.remainingInnings).isEqualTo(7)
        assertThat(analytics.averageScorePerInning).isEqualTo(4.5)
        assertThat(analytics.highestInningScore).isEqualTo(9)
        assertThat(analytics.shutoutInnings).isEqualTo(1)
        assertThat(analytics.winPercentage).isEqualTo(0.5)
        assertThat(analytics.isEstimate).isTrue()
    }

    private fun players(vararg pairs: Pair<String, String>): List<BaseballParticipant> =
        pairs.map { (id, name) ->
            BaseballParticipant(
                participantId = id,
                displayName = name,
                kind = BaseballParticipantKind.Player,
                playerIds = listOf(id),
                lineupOrder = listOf(id)
            )
        }

    private fun turn(
        id: String,
        gameId: String,
        participantId: String,
        teamId: String?,
        playerId: String,
        inning: Int,
        score: Int
    ): BaseballTurn {
        val target = ScoringRules.targetForInning(inning, ruleSet)
        return BaseballTurn(
            turnId = id,
            gameId = gameId,
            inningNumber = inning,
            targetNumber = target,
            participantId = participantId,
            teamId = teamId,
            playerId = playerId,
            lineupSlot = 1,
            darts = dartsForScore(target, score),
            enteredByUserId = "scorekeeper",
            createdAt = Instant.EPOCH,
            updatedAt = Instant.EPOCH
        )
    }

    private fun dartsForScore(target: Int, score: Int): List<BaseballDartThrow> {
        require(score in 0..9)
        var remaining = score
        return (1..3).map { index ->
            val ring = when {
                remaining >= 3 -> DartRing.Triple
                remaining == 2 -> DartRing.Double
                remaining == 1 -> DartRing.Single
                else -> DartRing.Miss
            }
            val value = when (ring) {
                DartRing.Triple -> 3
                DartRing.Double -> 2
                DartRing.Single -> 1
                else -> 0
            }
            remaining -= value
            dart(index, target, if (ring == DartRing.Miss) null else target, ring)
        }
    }

    private fun dart(index: Int, target: Int, landed: Int?, ring: DartRing, validity: DartValidity = DartValidity.Valid): BaseballDartThrow =
        BaseballDartThrow(
            dartThrowId = "dart-$target-$index-$ring",
            dartIndex = index,
            targetNumber = target,
            landedNumber = landed,
            ring = ring,
            validity = validity
        )
}
