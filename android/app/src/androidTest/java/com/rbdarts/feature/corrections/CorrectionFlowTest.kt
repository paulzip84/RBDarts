package com.rbdarts.feature.corrections

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class CorrectionFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun correctionWorkflowRenders() {
        composeRule.setContent { CorrectionWorkflowScreen() }
    }
}
