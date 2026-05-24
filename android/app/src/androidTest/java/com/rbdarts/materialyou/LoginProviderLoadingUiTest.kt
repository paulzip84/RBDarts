package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginProviderLoadingUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun selectedProviderShowsLoadingCopy() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = "Google"))
        }

        composeRule.assertVisibleText("Connecting to Google")
    }
}
