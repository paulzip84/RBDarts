package com.rbdarts.materialyou

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsAppShell
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AdaptiveNavigationTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun compactNavigationOpensTopLevelDrawerRoutes() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                RBDartsAppShell(
                    currentRoute = RBDartsRoute.Home,
                    configuration = RBDartsComposeTestHarness.configuration,
                    onDestinationSelected = {},
                    onSignOut = {}
                )
            }
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithText("Game type").assertExists()
        composeRule.onNodeWithText("Scoring").assertExists()
    }
}
