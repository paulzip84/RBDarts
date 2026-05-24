package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginProvidesBrandAndProviderDescriptions() {
        composeRule.setContent { LoginComposeTestHarness.Login() }

        composeRule.assertVisibleDescription("RBDarts mark")
        composeRule.assertVisibleDescription("Continue with Google using Google SSO")
        composeRule.assertVisibleDescription("Continue with Facebook using Facebook SSO")
    }
}
