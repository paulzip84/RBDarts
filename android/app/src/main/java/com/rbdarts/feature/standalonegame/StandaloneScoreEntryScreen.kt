package com.rbdarts.feature.standalonegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.core.domain.ScoringRules

interface StandaloneScoreEntryActions {
    fun enterScore(score: Int, participantId: String)
    fun undo(participantId: String)
    fun advanceInning()
    fun completeGame()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StandaloneScoreEntryScreen(
    state: StandaloneGameUiState,
    onAction: StandaloneScoreEntryActions
) {
    val session = state.session ?: return
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Inning ${state.currentInning}")
            OutlinedButton(onClick = onAction::advanceInning) { Text("Next") }
        }

        session.participants.forEach { participant ->
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("${participant.displayName} Total ${ScoringRules.participantTotal(participant)}")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    (0..9).forEach { score ->
                        Button(onClick = { onAction.enterScore(score, participant.participantId) }) {
                            Text("$score")
                        }
                    }
                }
                OutlinedButton(onClick = { onAction.undo(participant.participantId) }) {
                    Text("Undo inning score")
                }
            }
        }

        Button(onClick = onAction::completeGame) {
            Text("Complete game")
        }
    }
}
