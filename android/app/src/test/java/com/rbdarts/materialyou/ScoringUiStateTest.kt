package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.ScoreEntryUiState
import org.junit.Test

class ScoringUiStateTest {
    @Test
    fun submittedScoreUpdatesTotalsAndAdvancesParticipant() {
        val state = ScoreEntryUiState(pendingScore = "4").withSubmittedScore(4)

        assertThat(state.rawTotalFor("Player 1")).isEqualTo(4)
        assertThat(state.activeParticipant).isEqualTo("Player 2")
    }
}
