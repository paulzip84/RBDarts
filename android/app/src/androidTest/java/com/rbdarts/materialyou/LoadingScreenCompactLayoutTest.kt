package com.rbdarts.materialyou

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.feature.auth.LoadingScreen
import org.junit.Rule
import org.junit.Test

class LoadingScreenCompactLayoutTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun compactLayoutKeepsStatusVisible() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                Box(Modifier.width(320.dp).height(640.dp)) {
                    LoadingScreen(LaunchPresentationState(versionName = "0.1.0", buildNumber = "1"))
                }
            }
        }

        composeRule.onNodeWithTag("load_screen_status").assertIsDisplayed()
        composeRule.assertVisibleText("RBDarts")
    }
}
