package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginProviderDoubleTapTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun providerButtonsAreDisabledWhileProviderIsLoading() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = "Google"))
        }

        composeRule.onNodeWithContentDescription("Connecting to Google").assertIsNotEnabled()
        composeRule.onNodeWithContentDescription("Continue with Facebook using Facebook SSO").assertIsNotEnabled()
    }
}
