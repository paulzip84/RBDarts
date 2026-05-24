package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class LoadLoginPreservedByShellTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginDoesNotShowAuthenticatedHamburger() {
        composeRule.setContent {
            LoginComposeTestHarness.Login()
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").assertDoesNotExist()
        composeRule.assertVisibleText("Welcome back")
    }

    @Test
    fun loadingDoesNotShowAuthenticatedHamburger() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").assertDoesNotExist()
        composeRule.assertVisibleText("RBDarts")
    }
}
