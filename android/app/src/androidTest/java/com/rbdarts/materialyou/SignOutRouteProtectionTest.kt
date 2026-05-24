package com.rbdarts.materialyou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsAppShell
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.feature.auth.LoginScreen
import org.junit.Rule
import org.junit.Test

class SignOutRouteProtectionTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun signOutReturnsToLoginScreen() {
        var signedIn by mutableStateOf(true)
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                if (signedIn) {
                    RBDartsAppShell(
                        currentRoute = RBDartsRoute.Home,
                        configuration = RBDartsComposeTestHarness.configuration,
                        onDestinationSelected = {},
                        onSignOut = { signedIn = false }
                    )
                } else {
                    LoginScreen(
                        state = AuthUiState(),
                        configuration = RBDartsComposeTestHarness.configuration,
                        onGoogleSignIn = {},
                        onFacebookSignIn = {}
                    )
                }
            }
        }

        composeRule.onNodeWithText("Sign out").performClick()

        composeRule.assertVisibleText("Continue with Google")
    }
}
