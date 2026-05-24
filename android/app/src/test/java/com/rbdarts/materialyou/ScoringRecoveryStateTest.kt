package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.feature.scoring.ScoringViewModel
import org.junit.Test

class ScoringRecoveryStateTest {
    @Test
    fun recoveryLoadsActiveContext() {
        val viewModel = ScoringViewModel()

        viewModel.recoverSampleGame()

        assertThat(viewModel.uiState.value.scoreEntry.isRecovered).isTrue()
        assertThat(viewModel.uiState.value.scoreEntry.inning).isEqualTo(2)
    }
}
