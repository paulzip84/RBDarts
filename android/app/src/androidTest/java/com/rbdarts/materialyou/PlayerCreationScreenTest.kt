package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.rbdarts.feature.player.PlayerScreen
import com.rbdarts.feature.player.PlayerViewModel
import org.junit.Rule
import org.junit.Test

class PlayerCreationScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun playerFormSavesPlayer() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                PlayerScreen(viewModel = PlayerViewModel())
            }
        }

        composeRule.onNodeWithText("Player name").performTextInput("Jordan")
        composeRule.onNodeWithText("Nickname").performTextInput("J")
        composeRule.onNodeWithText("Seed average").performTextInput("41")
        composeRule.onNodeWithText("Save player").performClick()

        composeRule.assertVisibleText("Jordan is ready for scoring.")
    }
}
