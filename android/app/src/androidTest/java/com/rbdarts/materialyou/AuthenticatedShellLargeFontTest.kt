package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AuthenticatedShellLargeFontTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun drawerLabelsRemainReachableInShell() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Home)
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.assertVisibleText("Handicaps")
        composeRule.assertVisibleText("Sign Out")
    }
}
