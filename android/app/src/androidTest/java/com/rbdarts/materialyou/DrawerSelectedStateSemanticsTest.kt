package com.rbdarts.materialyou

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class DrawerSelectedStateSemanticsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun selectedDrawerDestinationExposesState() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Scoring)
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithContentDescription("Scoring")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Selected"))
    }
}
