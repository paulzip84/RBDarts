package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsAppShell
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AdaptiveNavigationTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun compactNavigationShowsTopLevelRoutes() {
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

        composeRule.assertVisibleText("Game")
        composeRule.assertVisibleText("Score")
    }
}
