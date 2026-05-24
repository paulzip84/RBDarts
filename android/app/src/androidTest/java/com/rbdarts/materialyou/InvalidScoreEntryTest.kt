package com.rbdarts.materialyou

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.feature.scoring.ScoringScreen
import com.rbdarts.feature.scoring.ScoringViewModel
import org.junit.Rule
import org.junit.Test

class InvalidScoreEntryTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun outOfRangePendingScoreDisablesSubmit() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                ScoringScreen(viewModel = ScoringViewModel())
            }
        }

        composeRule.onNodeWithText("9").performClick()
        composeRule.onNodeWithText("9").performClick()

        composeRule.onNodeWithText("Submit score").assertIsNotEnabled()
    }
}
