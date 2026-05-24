package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class DarkScoringWorkflowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scoringControlsRemainVisibleInDarkShell() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Scoring)
        }

        composeRule.assertVisibleText("Pending score: --")
        composeRule.assertVisibleText("Submit score")
        composeRule.assertVisibleText("Complete game")
    }
}
