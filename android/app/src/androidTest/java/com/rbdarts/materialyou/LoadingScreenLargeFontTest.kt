package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.LaunchPresentationState
import org.junit.Rule
import org.junit.Test

class LoadingScreenLargeFontTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun longSafeStatusCanWrapWithoutDroppingBrand() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(
                state = LaunchPresentationState(
                    versionName = "0.1.0",
                    buildNumber = "1",
                    loadingMessage = "Getting the board ready"
                )
            )
        }

        composeRule.assertVisibleText("RBDarts")
        composeRule.assertVisibleText("Getting the board ready")
    }
}
