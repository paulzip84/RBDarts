package com.rbdarts.feature.scoring

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.ui.ScoreEntryUiState

@Composable
fun ScoringContextHeader(
    state: ScoreEntryUiState,
    modifier: Modifier = Modifier
) {
    RBDartsInfoCard(
        title = "Inning ${state.inning}, target ${state.target}",
        body = "Active player: ${state.activeParticipant}. ${if (state.isRecovered) "Recovered active score session." else "Live score session."}",
        modifier = modifier
    )
}
