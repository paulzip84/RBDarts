package com.rbdarts.materialyou

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import org.junit.Rule
import org.junit.Test

class LoginNoPasswordUiTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loginUiDoesNotShowCredentialFieldsOrResetCopy() {
        composeRule.setContent { LoginComposeTestHarness.Login() }

        composeRule.onAllNodesWithText("password", substring = true, ignoreCase = true).assertCountEquals(0)
        composeRule.onAllNodesWithText("email", substring = true, ignoreCase = true).assertCountEquals(0)
        composeRule.onAllNodesWithText("forgot", substring = true, ignoreCase = true).assertCountEquals(0)
    }
}
