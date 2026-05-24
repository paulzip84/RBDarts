package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LoadingScreenFullBleedArtworkTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingScreenShowsOptimizedArtwork() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.onNodeWithTag("load_screen_artwork").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("RBDarts darts artwork").assertIsDisplayed()
    }
}
