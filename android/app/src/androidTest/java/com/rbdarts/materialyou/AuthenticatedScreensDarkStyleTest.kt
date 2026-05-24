package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AuthenticatedScreensDarkStyleTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun primaryAuthenticatedScreensRenderInShell() {
        val routesAndHeadlines = listOf(
            RBDartsRoute.Home to "Ready to play",
            RBDartsRoute.GameType to "Game type",
            RBDartsRoute.Players to "Players",
            RBDartsRoute.Seasons to "Seasons",
            RBDartsRoute.Handicaps to "Handicaps",
            RBDartsRoute.Scoring to "Scoring",
            RBDartsRoute.Settings to "Privacy and Support"
        )

        routesAndHeadlines.forEach { (route, headline) ->
            composeRule.setContent {
                AuthenticatedShellComposeTestHarness.Shell(currentRoute = route)
            }
            composeRule.assertVisibleText(headline)
            composeRule.assertVisibleDescription("Open navigation menu")
        }
    }
}
