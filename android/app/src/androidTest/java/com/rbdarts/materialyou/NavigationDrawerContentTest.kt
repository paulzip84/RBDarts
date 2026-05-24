package com.rbdarts.materialyou

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class NavigationDrawerContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun drawerContainsPrimaryDestinationsAndSignOut() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Home)
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()

        listOf("Home", "Game type", "Players", "Seasons", "Handicaps", "Scoring", "Settings", "Sign Out")
            .forEach { label ->
                composeRule.onNodeWithText(label).assertIsDisplayed()
            }
        composeRule.onNodeWithContentDescription("Home")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Selected"))
    }
}
