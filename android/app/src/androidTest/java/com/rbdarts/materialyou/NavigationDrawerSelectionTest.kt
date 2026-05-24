package com.rbdarts.materialyou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class NavigationDrawerSelectionTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun selectingDestinationNavigatesAndClosesDrawer() {
        var route by mutableStateOf(RBDartsRoute.Home)
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(
                currentRoute = route,
                onDestinationSelected = { route = it }
            )
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithText("Players").performClick()

        composeRule.assertVisibleText("Create player profiles once and reuse them across game setup, handicaps, and scoring.")
        composeRule.onNodeWithText("Sign Out").assertDoesNotExist()
    }
}
