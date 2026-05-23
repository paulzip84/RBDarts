package com.rbdarts.materialyou

import androidx.compose.runtime.Composable
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.feature.auth.LoadingScreen

object LoadScreenComposeTestHarness {
    fun defaultState(): LaunchPresentationState =
        LaunchPresentationState(versionName = "0.1.0", buildNumber = "1")

    @Composable
    fun Screen(
        state: LaunchPresentationState = defaultState(),
        darkTheme: Boolean = false
    ) {
        RBDartsComposeTestHarness.Surface(darkTheme = darkTheme) {
            LoadingScreen(state)
        }
    }
}
