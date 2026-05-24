package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginHelpLinkUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun providerSafeHelpLinkIsVisible() {
        composeRule.setContent { LoginComposeTestHarness.Login() }

        composeRule.assertVisibleText("Need help signing in?")
    }
}
