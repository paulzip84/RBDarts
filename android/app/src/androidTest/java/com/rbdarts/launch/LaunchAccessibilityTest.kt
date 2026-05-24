package com.rbdarts.launch

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.feature.settings.PrivacyAndSupportScreen
import com.rbdarts.feature.standalonegame.StandaloneGameSetupScreen
import org.junit.Rule
import org.junit.Test

class LaunchAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun primaryLaunchControlsHaveStableLabels() {
        composeRule.setContent {
            StandaloneGameSetupScreen {
                PrivacyAndSupportScreen(ReleaseConfiguration.defaultProduction())
            }
        }

        composeRule.onNodeWithText("Add player").assertIsDisplayed()
        composeRule.onNodeWithText("Start scoring").assertIsDisplayed()
        composeRule.onNodeWithText("Support").assertIsDisplayed()
    }
}
