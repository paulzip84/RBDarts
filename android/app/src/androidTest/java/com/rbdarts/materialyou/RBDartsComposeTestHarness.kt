package com.rbdarts.materialyou

import androidx.compose.runtime.Composable
import com.rbdarts.core.designsystem.RBDartsMaterialYouTheme
import com.rbdarts.core.launch.ReleaseConfiguration

object RBDartsComposeTestHarness {
    val configuration: ReleaseConfiguration = ReleaseConfiguration.defaultProduction()

    @Composable
    fun Surface(
        darkTheme: Boolean = false,
        content: @Composable () -> Unit
    ) {
        RBDartsMaterialYouTheme(useDynamicColor = false, darkTheme = darkTheme, content = content)
    }
}
