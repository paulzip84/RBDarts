package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class LoadingScreenNoLoginControlsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingScreenDoesNotShowLoginControls() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.onNodeWithText("Continue with Google").assertDoesNotExist()
        composeRule.onNodeWithText("Continue with Facebook").assertDoesNotExist()
        composeRule.onNodeWithText("Forgot Password?").assertDoesNotExist()
    }
}
