package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginProviderCancellationUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun cancellationKeepsRetryableLoginVisible() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(
                AuthUiState(
                    status = AuthFlowStatus.Cancelled,
                    selectedProvider = "Google",
                    errorMessage = "Google sign-in was cancelled."
                )
            )
        }

        composeRule.assertVisibleText("Sign-in cancelled")
        composeRule.assertVisibleText("Google sign-in was cancelled.")
        composeRule.assertVisibleText("Continue with Google")
    }
}
