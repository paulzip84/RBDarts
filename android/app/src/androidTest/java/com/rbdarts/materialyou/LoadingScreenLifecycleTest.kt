package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.LaunchPresentationState
import org.junit.Rule
import org.junit.Test

class LoadingScreenLifecycleTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun recreatedLoadingStateStillRendersSafeStatus() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(
                state = LaunchPresentationState(versionName = "0.1.0", buildNumber = "1")
            )
        }

        composeRule.assertVisibleText("Preparing secure darts session")

        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(
                state = LaunchPresentationState(versionName = "0.1.0", buildNumber = "1")
            )
        }

        composeRule.assertVisibleText("Preparing secure darts session")
    }
}
