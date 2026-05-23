package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.rbdarts.feature.handicap.HandicapScreen
import com.rbdarts.feature.handicap.HandicapViewModel
import org.junit.Rule
import org.junit.Test

class HandicapScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun editablePlayerHandicapCanBeSaved() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                HandicapScreen(viewModel = HandicapViewModel())
            }
        }

        composeRule.onNodeWithText("Morgan: handicap 2").performClick()
        composeRule.onNodeWithText("Override handicap").performTextInput("4")
        composeRule.onNodeWithText("Save handicap").performClick()

        composeRule.assertVisibleText("Morgan's handicap is ready for game setup.")
    }
}
