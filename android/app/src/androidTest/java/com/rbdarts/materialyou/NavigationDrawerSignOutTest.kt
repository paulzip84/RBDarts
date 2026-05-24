package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NavigationDrawerSignOutTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun selectingSignOutUsesDrawerAction() {
        var signOutCount = 0
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(
                currentRoute = RBDartsRoute.Home,
                onSignOut = { signOutCount += 1 }
            )
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").performClick()
        composeRule.onNodeWithText("Sign Out").performClick()

        assertEquals(1, signOutCount)
    }
}
