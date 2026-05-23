package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AccountSupportKind
import com.rbdarts.core.ui.AccountSupportLink
import com.rbdarts.core.ui.LoginPresentationState
import com.rbdarts.core.ui.SsoProviderId
import org.junit.Test

class LoginPresentationStateTest {
    @Test
    fun defaultPresentationShowsBrandWelcomeAndSsoProviders() {
        val state = LoginPresentationState(
            supportLinks = listOf(AccountSupportLink(AccountSupportKind.SignInHelp, "Need help signing in?", "https://rbdarts.app/support"))
        )

        assertThat(state.brandName).isEqualTo("RBDarts")
        assertThat(state.headline).isEqualTo("Welcome back")
        assertThat(state.providerActions.map { it.providerId }).containsExactly(SsoProviderId.Google, SsoProviderId.Facebook)
        assertThat(state.visibleText).contains("Need help signing in?")
    }
}
