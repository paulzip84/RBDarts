package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginSupportLinksUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun supportPrivacyAndAccountDeletionLinksAreReachable() {
        composeRule.setContent { LoginComposeTestHarness.Login() }

        composeRule.assertVisibleText("Support")
        composeRule.assertVisibleText("Privacy Policy")
        composeRule.assertVisibleText("Account Deletion")
    }
}
