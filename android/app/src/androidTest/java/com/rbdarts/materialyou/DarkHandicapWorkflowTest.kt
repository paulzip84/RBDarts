package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class DarkHandicapWorkflowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun handicapEditControlsRemainVisibleInDarkShell() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Handicaps)
        }

        composeRule.assertVisibleText("Override handicap")
        composeRule.assertVisibleText("Save handicap")
        composeRule.assertVisibleText("Scoring impact")
    }
}
