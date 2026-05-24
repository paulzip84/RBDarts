package com.rbdarts.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.core.designsystem.RBDartsAuthenticatedPanel
import com.rbdarts.core.designsystem.RBDartsAuthenticatedScreen
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun HomeScreen(
    onNavigate: (RBDartsRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    RBDartsAuthenticatedScreen(modifier = modifier) {
        RBDartsSectionHeader(
            title = "Ready to play",
            supportingText = "Set up players, seasons, handicaps, and scoring from one secure dark-mode dashboard."
        )
        RBDartsAuthenticatedPanel {
            RBDartsInfoCard(
                title = "Next game",
                body = "Standalone baseball darts is ready. Season match setup can use saved players and handicap values."
            )
        }
        RBDartsInfoCard(
            title = "Security posture",
            body = "Protected routes require sign-in, SSO actions avoid storing provider secrets in UI state, and diagnostics use redacted attributes."
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RBDartsPrimaryAction("Start scoring", onClick = { onNavigate(RBDartsRoute.Scoring) })
            RBDartsSecondaryAction("Configure game type", onClick = { onNavigate(RBDartsRoute.GameType) })
            RBDartsSecondaryAction("Manage players", onClick = { onNavigate(RBDartsRoute.Players) })
        }
    }
}
