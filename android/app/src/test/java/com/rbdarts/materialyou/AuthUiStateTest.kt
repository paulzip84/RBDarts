package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Test

class AuthUiStateTest {
    @Test
    fun loadingStateBlocksProviderSubmission() {
        val state = AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = "Google")

        assertThat(state.isLoading).isTrue()
        assertThat(state.canSubmit("Google")).isFalse()
    }
}
