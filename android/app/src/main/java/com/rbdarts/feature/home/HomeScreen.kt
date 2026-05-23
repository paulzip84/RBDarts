package com.rbdarts.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun HomeScreen(
    onNavigate: (RBDartsRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RBDartsSectionHeader(
            title = "Ready to play",
            supportingText = "Set up players, seasons, handicaps, and scoring from one Material You dashboard."
        )
        RBDartsInfoCard(
            title = "Next game",
            body = "Standalone baseball darts is ready. Season match setup can use saved players and handicap values."
        )
        RBDartsInfoCard(
            title = "Security posture",
            body = "Protected routes require sign-in, SSO actions avoid storing provider secrets in UI state, and diagnostics use redacted attributes."
        )
        RBDartsPrimaryAction("Start scoring", onClick = { onNavigate(RBDartsRoute.Scoring) })
        RBDartsSecondaryAction("Configure game type", onClick = { onNavigate(RBDartsRoute.GameType) })
        RBDartsSecondaryAction("Manage players", onClick = { onNavigate(RBDartsRoute.Players) })
    }
}
