package com.rbdarts.feature.standalonegame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rbdarts.core.domain.GameSummary

@Composable
fun StandaloneGameSummaryScreen(summary: GameSummary) {
    Column {
        Text("Final")
        Text(if (summary.winnerIds.isEmpty()) "No winner yet" else "Winner: ${summary.winnerIds.joinToString()}")
        Text("Margin: ${summary.margin}")
        Text("Innings played: ${summary.inningsPlayed}")
    }
}
