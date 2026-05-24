package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class NoBottomNavigationRegressionTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun closedAuthenticatedShellDoesNotShowOldBottomNavigationLabels() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Home)
        }

        composeRule.onNodeWithText("Game").assertDoesNotExist()
        composeRule.onNodeWithText("Hcap").assertDoesNotExist()
        composeRule.onNodeWithText("Score").assertDoesNotExist()
    }
}
