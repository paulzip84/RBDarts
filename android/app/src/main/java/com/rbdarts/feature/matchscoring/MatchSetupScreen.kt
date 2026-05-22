package com.rbdarts.feature.matchscoring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun MatchSetupScreen() {
    val homeTeam = remember { mutableStateOf("Home Team") }
    val awayTeam = remember { mutableStateOf("Away Team") }

    Column {
        OutlinedTextField(
            value = homeTeam.value,
            onValueChange = { homeTeam.value = it },
            label = { Text("Home team") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = awayTeam.value,
            onValueChange = { awayTeam.value = it },
            label = { Text("Away team") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Lineup averages are snapshotted when the first game starts.")
    }
}
