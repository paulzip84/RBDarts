package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.core.ui.LoadArtworkAsset
import com.rbdarts.core.ui.LoadArtworkMode
import org.junit.Rule
import org.junit.Test

class LoadingScreenFallbackTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun fallbackArtworkStillShowsBrandAndStatus() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(
                state = LaunchPresentationState(
                    versionName = "0.1.0",
                    buildNumber = "1",
                    artwork = LoadArtworkAsset(mode = LoadArtworkMode.Fallback)
                )
            )
        }

        composeRule.onNodeWithTag("load_screen_fallback_artwork").assertIsDisplayed()
        composeRule.assertVisibleText("RBDarts")
        composeRule.assertVisibleText("Preparing secure darts session")
    }
}
