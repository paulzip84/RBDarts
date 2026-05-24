package com.rbdarts.materialyou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsAppShell
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun authenticatedShellNavigatesToScoring() {
        var route by mutableStateOf(RBDartsRoute.Home)
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                RBDartsAppShell(
                    currentRoute = route,
                    configuration = RBDartsComposeTestHarness.configuration,
                    onDestinationSelected = { route = it },
                    onSignOut = {}
                )
            }
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithText("Scoring").performClick()
        composeRule.assertVisibleText("Scoring")
    }
}
