package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoginDarkThemeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginRendersInDarkTheme() {
        composeRule.setContent { LoginComposeTestHarness.Login(darkTheme = true) }

        composeRule.assertVisibleText("Welcome back")
        composeRule.assertVisibleText("Need help signing in?")
    }
}
