package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.feature.auth.LoadingScreen
import org.junit.Rule
import org.junit.Test

class LargeFontSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingContentStillHasVisibleBrand() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                LoadingScreen(LaunchPresentationState(versionName = "0.1.0", buildNumber = "1"))
            }
        }

        composeRule.assertVisibleText("RBDarts")
    }
}
