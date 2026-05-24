package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginLightThemeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginRendersWithLightThemeFallback() {
        composeRule.setContent { LoginComposeTestHarness.Login(darkTheme = false) }

        composeRule.assertVisibleText("Welcome back")
        composeRule.assertVisibleText("Continue with Google")
    }
}
