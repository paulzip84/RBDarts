package com.rbdarts.feature.league

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun LeagueSettingsScreen() {
    val leagueName = remember { mutableStateOf("Wednesday Baseball Darts") }
    val gamesPerMatch = remember { mutableIntStateOf(3) }
    val playersPerTeam = remember { mutableIntStateOf(4) }
    val handicapPercent = remember { mutableDoubleStateOf(80.0) }

    Column {
        OutlinedTextField(
            value = leagueName.value,
            onValueChange = { leagueName.value = it },
            label = { Text("League name") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("Games per match: ${gamesPerMatch.intValue}")
        Slider(
            value = gamesPerMatch.intValue.toFloat(),
            onValueChange = { gamesPerMatch.intValue = it.toInt().coerceIn(1, 12) },
            valueRange = 1f..12f,
            steps = 10
        )
        Text("Players per team: ${playersPerTeam.intValue}")
        Slider(
            value = playersPerTeam.intValue.toFloat(),
            onValueChange = { playersPerTeam.intValue = it.toInt().coerceIn(1, 8) },
            valueRange = 1f..8f,
            steps = 6
        )
        Text("Handicap ${handicapPercent.doubleValue.toInt()}%")
        Slider(
            value = handicapPercent.doubleValue.toFloat(),
            onValueChange = { handicapPercent.doubleValue = it.toDouble() },
            valueRange = 0f..100f
        )
    }
}
