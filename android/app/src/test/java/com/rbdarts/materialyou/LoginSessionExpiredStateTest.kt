package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.LoginPresentationState
import org.junit.Test

class LoginSessionExpiredStateTest {
    @Test
    fun sessionExpiredPromptsUserToSignInAgain() {
        val state = LoginPresentationState.fromAuthState(AuthUiState(status = AuthFlowStatus.SessionExpired))

        assertThat(state.message?.title).isEqualTo("Session expired")
        assertThat(state.message?.reasonCode).isEqualTo("session_expired")
    }
}
