package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LoadingScreenProgressStateTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingStatusAndProgressAreVisible() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.assertVisibleText("Preparing secure darts session")
        composeRule.onNodeWithTag("load_screen_progress").assertIsDisplayed()
    }
}
