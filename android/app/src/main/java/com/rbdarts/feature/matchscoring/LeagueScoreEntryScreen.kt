package com.rbdarts.feature.matchscoring

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rbdarts.core.domain.HandicapResult

@Composable
fun LeagueScoreEntryScreen(gameNumber: Int, handicap: HandicapResult) {
    Column {
        Text("Game $gameNumber")
        if (handicap.lowerAverageTeamId != null) {
            Text("${handicap.lowerAverageTeamId} receives ${handicap.handicap} handicap")
        } else {
            Text("No handicap")
        }
        Text("Starting the next game locks the prior completed game.")
    }
}
