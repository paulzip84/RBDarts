package com.rbdarts.materialyou

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import com.rbdarts.core.designsystem.RBDartsMaterialYouTheme
import org.junit.Rule
import org.junit.Test

class ThemeModeSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun darkThemeRendersContent() {
        composeRule.setContent {
            RBDartsMaterialYouTheme(useDynamicColor = false, darkTheme = true) {
                Text("Dark mode ready")
            }
        }

        composeRule.assertVisibleText("Dark mode ready")
    }
}
