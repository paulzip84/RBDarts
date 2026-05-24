package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class DarkWorkflowFormsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun playerAndSeasonFormsRemainVisibleInDarkShell() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Players)
        }
        composeRule.assertVisibleText("Player name")
        composeRule.assertVisibleText("Save player")

        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Seasons)
        }
        composeRule.assertVisibleText("Season name")
        composeRule.assertVisibleText("Save season")
    }
}
