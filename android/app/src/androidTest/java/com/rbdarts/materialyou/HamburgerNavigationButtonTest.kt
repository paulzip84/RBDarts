package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.rbdarts.app.RBDartsRoute
import org.junit.Rule
import org.junit.Test

class HamburgerNavigationButtonTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun authenticatedShellShowsTopLeftHamburger() {
        composeRule.setContent {
            AuthenticatedShellComposeTestHarness.Shell(currentRoute = RBDartsRoute.Home)
        }

        composeRule.onNodeWithContentDescription("Open navigation menu").assertIsDisplayed()
    }
}
