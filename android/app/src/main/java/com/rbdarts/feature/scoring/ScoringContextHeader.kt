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
        body = buildString {
            append("Active player: ${state.activeParticipant}. ")
            state.leaderName?.let { leader ->
                append("$leader leads by ${state.leadMargin}. ")
            } ?: append("Game is tied. ")
            append("${state.remainingInnings} regulation innings remaining. ")
            append(if (state.isRecovered) "Recovered active score session." else "Live score session.")
        },
        modifier = modifier
    )
}
