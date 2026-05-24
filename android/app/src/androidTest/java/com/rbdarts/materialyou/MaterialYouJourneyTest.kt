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

class MaterialYouJourneyTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userCanMoveAcrossCoreSections() {
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
        composeRule.onNodeWithText("Players").performClick()
        composeRule.assertVisibleText("Create player profiles once and reuse them across game setup, handicaps, and scoring.")
        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithText("Handicaps").performClick()
        composeRule.assertVisibleText("Review derived handicap values and apply player-level overrides when permitted.")
    }
}
