package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LoadingScreenThemeContrastTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun darkThemeKeepsBrandVisible() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(darkTheme = true)
        }

        composeRule.assertVisibleText("RBDarts")
    }

    @Test
    fun lightThemeKeepsBrandVisible() {
        composeRule.setContent {
            LoadScreenComposeTestHarness.Screen(darkTheme = false)
        }

        composeRule.assertVisibleText("RBDarts")
    }
}
