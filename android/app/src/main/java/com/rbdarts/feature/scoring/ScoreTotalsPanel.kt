package com.rbdarts.feature.scoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.ui.ScoreEntryUiState

@Composable
fun ScoreTotalsPanel(
    state: ScoreEntryUiState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        state.participants.forEach { participant ->
            RBDartsInfoCard(
                title = participant.name,
                body = "Raw ${state.rawTotalFor(participant.name)}. Handicap ${participant.handicap}. Adjusted ${state.adjustedTotalFor(participant.name)}."
            )
        }
    }
}
