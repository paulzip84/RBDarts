package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.ProviderInteractionState
import com.rbdarts.core.ui.SsoProviderId
import com.rbdarts.core.ui.defaultProviderActions
import org.junit.Test

class SsoProviderActionTest {
    @Test
    fun selectedProviderLoadingDisablesEveryProviderAction() {
        val actions = defaultProviderActions(AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = "Google"))

        assertThat(actions.first { it.providerId == SsoProviderId.Google }.interactionState)
            .isEqualTo(ProviderInteractionState.Loading)
        assertThat(actions.all { !it.enabled }).isTrue()
    }
}
