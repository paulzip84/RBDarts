package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class ScoringShellPerformanceSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scoreInputStillAcceptsFastLocalTaps() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Scoring)
        }

        composeRule.onNodeWithText("2").performClick()
        composeRule.onNodeWithText("5").performClick()
        composeRule.assertVisibleText("Pending score: 25")
    }
}
