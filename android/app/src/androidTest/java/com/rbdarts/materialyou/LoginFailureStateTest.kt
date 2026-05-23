package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.feature.auth.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginFailureStateTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginScreenKeepsUserOnFailure() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                LoginScreen(
                    state = AuthUiState(
                        status = AuthFlowStatus.Failed,
                        selectedProvider = "Google",
                        errorMessage = "Google sign-in is temporarily unavailable."
                    ),
                    configuration = RBDartsComposeTestHarness.configuration,
                    onGoogleSignIn = {},
                    onFacebookSignIn = {}
                )
            }
        }

        composeRule.assertVisibleText("Sign-in needs attention")
        composeRule.assertVisibleText("Google sign-in is temporarily unavailable.")
    }
}
