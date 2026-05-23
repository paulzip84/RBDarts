package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginPremiumScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun premiumLoginScreenShowsBrandWelcomeAndProviders() {
        composeRule.setContent { LoginComposeTestHarness.Login() }

        composeRule.assertVisibleText("Welcome back")
        composeRule.assertVisibleText("RBDarts")
        composeRule.assertVisibleText("Secure SSO")
        composeRule.assertVisibleText("Continue with Google")
        composeRule.assertVisibleText("Continue with Facebook")
    }
}
