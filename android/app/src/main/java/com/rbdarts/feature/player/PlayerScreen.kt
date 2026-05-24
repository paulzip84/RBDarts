package com.rbdarts.feature.player

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbdarts.core.designsystem.RBDartsAuthenticatedScreen
import com.rbdarts.core.designsystem.RBDartsErrorState
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    RBDartsAuthenticatedScreen(modifier = modifier) {
        RBDartsSectionHeader(
            title = "Players",
            supportingText = "Create player profiles once and reuse them across game setup, handicaps, and scoring."
        )
        OutlinedTextField(
            value = state.draft.displayName,
            onValueChange = viewModel::updateDisplayName,
            label = { Text("Player name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.draft.nickname,
            onValueChange = viewModel::updateNickname,
            label = { Text("Nickname") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.draft.seedAverageText,
            onValueChange = viewModel::updateSeedAverage,
            label = { Text("Seed average") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        state.message?.let { message ->
            if (state.draft.validationErrors.isEmpty()) {
                RBDartsInfoCard(title = "Player update", body = message)
            } else {
                RBDartsErrorState(title = "Check player details", body = message)
            }
        }
        RBDartsPrimaryAction("Save player", onClick = viewModel::savePlayer, enabled = state.draft.canSave)
        state.players.forEach { player ->
            RBDartsInfoCard(
                title = player.displayName,
                body = "Nickname: ${player.nickname.ifBlank { "None" }}. Seed average: ${player.seedAverage}."
            )
        }
    }
}
