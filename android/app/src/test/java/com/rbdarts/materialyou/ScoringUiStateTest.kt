package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.ScoreEntryUiState
import com.rbdarts.core.ui.ScoringParticipant
import org.junit.Test

class ScoringUiStateTest {
    @Test
    fun submittedScoreUpdatesTotalsAndAdvancesParticipant() {
        val state = ScoreEntryUiState(pendingScore = "4").withSubmittedScore(4)

        assertThat(state.rawTotalFor("Player 1")).isEqualTo(4)
        assertThat(state.activeParticipant).isEqualTo("Player 2")
    }

    @Test
    fun tiedRegulationGameAdvancesToExtraInning() {
        val entries = mapOf(
            "Player 1" to List(8) { 5 },
            "Player 2" to List(8) { 5 }
        )
        val afterPlayerOne = ScoreEntryUiState(
            inning = 9,
            target = 9,
            activeParticipant = "Player 1",
            participants = listOf(ScoringParticipant("Player 1"), ScoringParticipant("Player 2")),
            entries = entries,
            pendingScore = "5"
        ).withSubmittedScore(5)

        val afterPlayerTwo = afterPlayerOne.copy(pendingScore = "5").withSubmittedScore(5)

        assertThat(afterPlayerTwo.inning).isEqualTo(10)
        assertThat(afterPlayerTwo.target).isEqualTo(10)
        assertThat(afterPlayerTwo.isComplete).isFalse()
    }

    @Test
    fun regulationGameCompletesWhenWinnerExistsAfterNinth() {
        val entries = mapOf(
            "Player 1" to List(8) { 5 },
            "Player 2" to List(8) { 5 }
        )
        val afterPlayerOne = ScoreEntryUiState(
            inning = 9,
            target = 9,
            activeParticipant = "Player 1",
            participants = listOf(ScoringParticipant("Player 1"), ScoringParticipant("Player 2")),
            entries = entries,
            pendingScore = "6"
        ).withSubmittedScore(6)

        val afterPlayerTwo = afterPlayerOne.copy(pendingScore = "5").withSubmittedScore(5)

        assertThat(afterPlayerTwo.isComplete).isTrue()
        assertThat(afterPlayerTwo.leaderName).isEqualTo("Player 1")
        assertThat(afterPlayerTwo.leadMargin).isEqualTo(1)
    }
}
