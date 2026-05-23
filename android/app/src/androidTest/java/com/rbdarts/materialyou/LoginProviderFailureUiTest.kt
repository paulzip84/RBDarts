package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginProviderFailureUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun providerFailureShowsSafeMessage() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(
                AuthUiState(
                    status = AuthFlowStatus.Failed,
                    selectedProvider = "Facebook",
                    errorMessage = "Unable to sign in with Facebook. Please try again."
                )
            )
        }

        composeRule.assertVisibleText("Sign-in needs attention")
        composeRule.assertVisibleText("Unable to sign in with Facebook. Please try again.")
    }
}
