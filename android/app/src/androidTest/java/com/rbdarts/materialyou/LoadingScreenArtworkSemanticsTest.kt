package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class LoadingScreenArtworkSemanticsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun artworkHasConciseDescription() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.onNodeWithContentDescription("RBDarts darts artwork").assertIsDisplayed()
    }
}
