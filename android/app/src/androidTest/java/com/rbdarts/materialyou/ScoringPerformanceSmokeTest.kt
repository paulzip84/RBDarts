package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.feature.scoring.ScoringScreen
import com.rbdarts.feature.scoring.ScoringViewModel
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ScoringPerformanceSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scoreEntryRespondsWithinSmokeThreshold() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                ScoringScreen(viewModel = ScoringViewModel())
            }
        }

        val startedAt = System.nanoTime()
        composeRule.onNodeWithText("5").performClick()
        composeRule.onNodeWithText("Submit score").performClick()
        val elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000

        assertTrue("Score entry took ${elapsedMillis}ms", elapsedMillis < 2_000)
    }
}
