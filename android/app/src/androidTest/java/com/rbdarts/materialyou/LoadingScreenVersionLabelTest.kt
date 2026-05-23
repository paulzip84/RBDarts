package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoadingScreenVersionLabelTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun versionLabelIsVisible() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen()
        }

        composeRule.assertVisibleText("Version 0.1.0 (1)")
    }
}
