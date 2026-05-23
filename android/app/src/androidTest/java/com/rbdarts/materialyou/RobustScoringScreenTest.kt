package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.feature.scoring.ScoringScreen
import com.rbdarts.feature.scoring.ScoringViewModel
import org.junit.Rule
import org.junit.Test

class RobustScoringScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun scoringScreenSubmitsScoreAndUpdatesTotals() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                ScoringScreen(viewModel = ScoringViewModel())
            }
        }

        composeRule.onNodeWithText("4").performClick()
        composeRule.onNodeWithText("Submit score").performClick()

        composeRule.assertVisibleText("Raw 4. Handicap 3. Adjusted 7.")
    }
}
