package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AccountSupportKind
import com.rbdarts.core.ui.AccountSupportLink
import org.junit.Test

class LoginSupportLinkStateTest {
    @Test
    fun signInHelpLinkUsesProviderSafeLanguage() {
        val link = AccountSupportLink(AccountSupportKind.SignInHelp, "Need help signing in?", "https://rbdarts.app/support")

        assertThat(link.label).doesNotContain("Password")
        assertThat(link.url).startsWith("https://")
    }
}
