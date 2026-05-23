package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import org.junit.Rule
import org.junit.Test

class LoginSessionExpiredRouteTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun sessionExpiredStateShowsLoginRecovery() {
        composeRule.setContent {
            LoginComposeTestHarness.Login(AuthUiState(status = AuthFlowStatus.SessionExpired))
        }

        composeRule.assertVisibleText("Session expired")
        composeRule.assertVisibleText("Please sign in again to continue.")
        composeRule.assertVisibleText("Continue with Google")
    }
}
