package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.LoginPresentationState
import org.junit.Test

class LoginProviderLoadingStateTest {
    @Test
    fun selectedProviderShowsConnectingLabel() {
        val state = LoginPresentationState.fromAuthState(
            AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = "Google")
        )

        assertThat(state.providerActions.map { it.label }).contains("Connecting to Google")
        assertThat(state.providerActions.all { !it.canClick }).isTrue()
    }
}
