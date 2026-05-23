package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.feature.auth.LoadingScreen
import org.junit.Rule
import org.junit.Test

class LoadingAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingScreenHasAccessibleStatusDescriptions() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                LoadingScreen(LaunchPresentationState(versionName = "0.1.0", buildNumber = "1"))
            }
        }

        composeRule.assertVisibleDescription("RBDarts loading screen")
        composeRule.assertVisibleDescription("RBDarts darts artwork")
        composeRule.assertVisibleText("Preparing secure darts session")
    }
}
