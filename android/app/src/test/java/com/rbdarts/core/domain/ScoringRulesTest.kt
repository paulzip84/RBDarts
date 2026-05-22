package com.rbdarts.core.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.Assert.assertThrows

class ScoringRulesTest {
    @Test
    fun standaloneBasicTotals() {
        val participants = listOf(
            ParticipantScorecard("player-a", "Avery", mapOf(1 to 1, 2 to 2, 3 to 3, 4 to 4, 5 to 5, 6 to 6, 7 to 7, 8 to 8, 9 to 9)),
            ParticipantScorecard("player-b", "Blake", mapOf(1 to 0, 2 to 3, 3 to 3, 4 to 4, 5 to 4, 6 to 5, 7 to 6, 8 to 7, 9 to 8))
        )

        val summary = ScoringRules.summarizeStandalone(participants)

        assertThat(summary.participantSummaries.first { it.participantId == "player-a" }.total).isEqualTo(45)
        assertThat(summary.participantSummaries.first { it.participantId == "player-b" }.total).isEqualTo(40)
        assertThat(summary.winnerIds).containsExactly("player-a")
        assertThat(summary.margin).isEqualTo(5)
    }

    @Test
    fun invalidScoresAreRejected() {
        assertThrows(ScoreValidationException::class.java) { ScoringRules.validateScore(-1) }
        assertThrows(ScoreValidationException::class.java) { ScoringRules.validateScore(10) }
    }

    @Test
    fun handicapRoundingMatchesFixture() {
        val averages = mapOf("higher" to 72.0, "lower" to 63.4)

        assertThat(ScoringRules.handicap(averages, 80.0, HandicapRoundingRule.Nearest).handicap).isEqualTo(7.0)
        assertThat(ScoringRules.handicap(averages, 80.0, HandicapRoundingRule.Floor).handicap).isEqualTo(6.0)
        assertThat(ScoringRules.handicap(averages, 80.0, HandicapRoundingRule.Ceiling).handicap).isEqualTo(7.0)
        assertThat(ScoringRules.handicap(averages, 80.0, HandicapRoundingRule.Decimal).handicap).isWithin(0.001).of(6.88)
    }
}
