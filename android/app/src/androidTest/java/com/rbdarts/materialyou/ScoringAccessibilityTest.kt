package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.feature.scoring.ScoringScreen
import com.rbdarts.feature.scoring.ScoringViewModel
import org.junit.Rule
import org.junit.Test

class ScoringAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scoringScreenHasContextAndActionLabels() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                ScoringScreen(viewModel = ScoringViewModel())
            }
        }

        composeRule.assertVisibleText("Inning 1, target 1")
        composeRule.assertVisibleText("Submit score")
    }
}
