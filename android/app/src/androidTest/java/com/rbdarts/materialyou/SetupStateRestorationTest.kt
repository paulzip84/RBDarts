package com.rbdarts.materialyou

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.feature.gametype.GameTypeScreen
import org.junit.Rule
import org.junit.Test

class SetupStateRestorationTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun gameTypeSelectionUpdatesSummary() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                GameTypeScreen(onNavigate = {})
            }
        }

        composeRule.onNodeWithText("Choose Season match").performClick()

        composeRule.assertVisibleText("Selected: Season match. Players: Avery and Morgan. Season: Spring 2026. Handicap mode: individual player level.")
    }
}
