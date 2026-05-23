package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.feature.auth.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginScreenShowsSsoProviders() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                LoginScreen(
                    state = AuthUiState(),
                    configuration = RBDartsComposeTestHarness.configuration,
                    onGoogleSignIn = {},
                    onFacebookSignIn = {}
                )
            }
        }

        composeRule.assertVisibleText("Continue with Google")
        composeRule.assertVisibleText("Continue with Facebook")
    }
}
