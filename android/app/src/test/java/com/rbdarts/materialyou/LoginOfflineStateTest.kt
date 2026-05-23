package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.LoginPresentationState
import org.junit.Test

class LoginOfflineStateTest {
    @Test
    fun offlineStateExplainsConnectionRequirement() {
        val state = LoginPresentationState.fromAuthState(AuthUiState(status = AuthFlowStatus.Offline))

        assertThat(state.message?.title).isEqualTo("Connection required")
        assertThat(state.message?.reasonCode).isEqualTo("offline")
    }
}
