package com.rbdarts.feature.stats

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class SummaryFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun statsDashboardRenders() {
        composeRule.setContent { StatsDashboardScreen(listOf(ProjectionResult("Estimate", 42.0))) }
    }
}
