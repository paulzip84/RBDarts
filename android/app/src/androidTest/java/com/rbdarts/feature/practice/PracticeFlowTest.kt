package com.rbdarts.feature.practice

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class PracticeFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun practiceScreenRenders() {
        composeRule.setContent { PracticeScreen() }
    }
}
