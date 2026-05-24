package com.rbdarts.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.designsystem.RBDartsLoadScreenSurface
import com.rbdarts.core.ui.LaunchPresentationState

@Composable
fun LoadingScreen(
    state: LaunchPresentationState,
    modifier: Modifier = Modifier
) {
    RBDartsLoadScreenSurface(state = state, modifier = modifier)
}
