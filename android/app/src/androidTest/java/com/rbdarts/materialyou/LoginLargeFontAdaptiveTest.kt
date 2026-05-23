package com.rbdarts.materialyou

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class LoginLargeFontAdaptiveTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun compactLoginWidthKeepsPrimaryActionsVisible() {
        composeRule.setContent {
            Box(Modifier.width(360.dp)) {
                LoginComposeTestHarness.Login()
            }
        }

        composeRule.assertVisibleText("Continue with Google")
        composeRule.assertVisibleText("Continue with Facebook")
    }
}
