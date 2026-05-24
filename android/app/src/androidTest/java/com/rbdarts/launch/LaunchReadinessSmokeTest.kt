package com.rbdarts.launch

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.feature.settings.PrivacyAndSupportScreen
import com.rbdarts.feature.standalonegame.StandaloneGameSetupScreen
import org.junit.Rule
import org.junit.Test

class LaunchReadinessSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun launchReadinessSurfaceRenders() {
        composeRule.setContent {
            StandaloneGameSetupScreen {
                PrivacyAndSupportScreen(ReleaseConfiguration.defaultProduction())
            }
        }

        composeRule.onNodeWithText("RBDarts").assertIsDisplayed()
        composeRule.onNodeWithText("Game name").assertIsDisplayed()
        composeRule.onNodeWithText("Privacy Policy").assertIsDisplayed()
        composeRule.onNodeWithText("Account Deletion").assertIsDisplayed()
    }
}
