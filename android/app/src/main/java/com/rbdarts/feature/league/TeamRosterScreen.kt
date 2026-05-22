package com.rbdarts.feature.league

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TeamRosterScreen() {
    val teamName = remember { mutableStateOf("Home Team") }
    val players = remember { mutableStateListOf("Player 1", "Player 2", "Player 3", "Player 4") }

    Column {
        OutlinedTextField(
            value = teamName.value,
            onValueChange = { teamName.value = it },
            label = { Text("Team name") },
            modifier = Modifier.fillMaxWidth()
        )
        players.forEachIndexed { index, playerName ->
            OutlinedTextField(
                value = playerName,
                onValueChange = { players[index] = it },
                label = { Text("Player ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(onClick = { players += "Player ${players.size + 1}" }) {
            Text("Add roster player")
        }
    }
}
