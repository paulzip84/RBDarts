package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class AuthenticatedShellDarkHomeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun homeRendersInsideAuthenticatedShell() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Home)
        }

        composeRule.assertVisibleText("Ready to play")
        composeRule.assertVisibleDescription("Open navigation menu")
    }
}
