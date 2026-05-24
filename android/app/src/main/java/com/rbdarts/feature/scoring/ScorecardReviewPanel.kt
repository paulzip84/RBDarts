package com.rbdarts.feature.scoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.ui.ScoreEntryUiState

@Composable
fun ScorecardReviewPanel(
    state: ScoreEntryUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (state.entries.isEmpty()) {
            RBDartsInfoCard("Scorecard", "No scores entered yet.")
        } else {
            state.entries.forEach { (player, scores) ->
                RBDartsInfoCard(player, scores.joinToString(prefix = "Scores: "))
            }
        }
        if (state.isComplete) {
            RBDartsInfoCard("Complete", "Scorecard is ready for review and save.")
        }
    }
}
