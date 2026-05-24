package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.LoginPresentationState
import org.junit.Test

class LoginProviderFailureStateTest {
    @Test
    fun providerUnavailableCreatesSafeMessage() {
        val state = LoginPresentationState.fromAuthState(
            AuthUiState(status = AuthFlowStatus.Unavailable, selectedProvider = "Facebook")
        )

        assertThat(state.message?.title).isEqualTo("Provider unavailable")
        assertThat(state.message?.reasonCode).isEqualTo("provider_unavailable")
    }
}
