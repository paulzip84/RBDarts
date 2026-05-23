package com.rbdarts.feature.handicap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.ui.PlayerHandicapState

@Composable
fun HandicapImpactPreview(
    player: PlayerHandicapState,
    modifier: Modifier = Modifier
) {
    RBDartsInfoCard(
        title = "Scoring impact",
        body = "${player.playerName}: recent average ${player.recentAverage}, handicap ${player.derivedHandicap}, projected total ${player.projectedScoreWithHandicap}.",
        modifier = modifier
    )
}
