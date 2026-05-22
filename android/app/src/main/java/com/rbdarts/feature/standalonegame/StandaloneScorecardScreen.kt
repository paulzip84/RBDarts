package com.rbdarts.feature.standalonegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.core.domain.GameSummary

@Composable
fun StandaloneScorecardScreen(summary: GameSummary?) {
    if (summary == null) return
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        summary.participantSummaries.forEach { participant ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(participant.displayName)
                Text("${participant.total}")
                Text("0s ${participant.zeroCount}")
                Text("9s ${participant.nineCount}")
            }
        }
        if (summary.needsExtraInning) {
            Text("Tie after regulation. Continue to the next inning.")
        }
    }
}
