package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.feature.handicap.HandicapScreen
import com.rbdarts.feature.handicap.HandicapViewModel
import org.junit.Rule
import org.junit.Test

class HandicapAccessibilityTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun handicapScreenExposesReadableLabels() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                HandicapScreen(viewModel = HandicapViewModel())
            }
        }

        composeRule.assertVisibleText("Handicaps")
        composeRule.assertVisibleText("Save handicap")
    }
}
