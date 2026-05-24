package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.rbdarts.feature.season.SeasonScreen
import com.rbdarts.feature.season.SeasonViewModel
import org.junit.Rule
import org.junit.Test

class SeasonCreationScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun seasonFormSavesSeason() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                SeasonScreen(viewModel = SeasonViewModel())
            }
        }

        composeRule.onNodeWithText("Season name").performTextInput("Summer 2026")
        composeRule.onNodeWithText("Start date").performTextInput("2026-06-01")
        composeRule.onNodeWithText("End date").performTextInput("2026-08-31")
        composeRule.onNodeWithText("Save season").performClick()

        composeRule.assertVisibleText("Summer 2026 season is ready.")
    }
}
