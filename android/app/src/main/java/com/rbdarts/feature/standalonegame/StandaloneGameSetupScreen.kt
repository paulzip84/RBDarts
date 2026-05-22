package com.rbdarts.feature.standalonegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StandaloneGameSetupScreen(
    viewModel: StandaloneGameViewModel = remember { StandaloneGameViewModel() },
    footerContent: (@Composable () -> Unit)? = null
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("RBDarts", style = MaterialTheme.typography.headlineMedium)
        }
        footerContent?.let { content ->
            item {
                content()
            }
        }
        item {
            OutlinedTextField(
                value = state.gameName,
                onValueChange = viewModel::updateGameName,
                label = { Text("Game name") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(state.playerNames.indices.toList()) { index ->
            OutlinedTextField(
                value = state.playerNames[index],
                onValueChange = { viewModel.updatePlayerName(index, it) },
                label = { Text("Player ${index + 1}") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = viewModel::addPlayer) { Text("Add player") }
                Button(onClick = viewModel::startGame) { Text("Start scoring") }
            }
        }
        state.session?.let {
            item {
                StandaloneScoreEntryScreen(state = state, onAction = viewModel)
            }
            item {
                StandaloneScorecardScreen(summary = state.liveSummary)
            }
        }
        state.summary?.let { summary ->
            item {
                StandaloneGameSummaryScreen(summary = summary)
            }
        }
        state.errorMessage?.let { message ->
            item {
                Text(message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
