package com.rbdarts.materialyou

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class MaterialYouThemeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun themeRendersContent() {
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface {
                Text("Material You ready")
            }
        }

        composeRule.assertVisibleText("Material You ready")
    }
}
