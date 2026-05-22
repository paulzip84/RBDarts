package com.rbdarts.feature.league

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.domain.ScoringRules
import com.rbdarts.core.domain.TeamStanding

@Composable
fun StandingsScreen(standings: List<TeamStanding>) {
    LazyColumn {
        items(ScoringRules.rankedStandings(standings)) { standing ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(standing.teamId, modifier = Modifier.weight(1f))
                Text("${standing.leaguePoints} pts")
                Text(" W ${standing.gamesWon}")
            }
        }
    }
}
