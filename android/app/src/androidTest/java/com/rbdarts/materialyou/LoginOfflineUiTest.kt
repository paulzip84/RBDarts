package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginOfflineUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun offlineStateShowsConnectionMessage() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(AuthUiState(status = AuthFlowStatus.Offline))
        }

        composeRule.assertVisibleText("Connection required")
        composeRule.assertVisibleText("SSO requires an internet connection. Reconnect and try again.")
    }
}
