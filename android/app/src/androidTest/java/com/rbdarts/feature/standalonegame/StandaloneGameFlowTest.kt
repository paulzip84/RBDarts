package com.rbdarts.feature.standalonegame

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class StandaloneGameFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun standaloneGameSetupRenders() {
        composeRule.setContent { StandaloneGameSetupScreen() }
    }
}
