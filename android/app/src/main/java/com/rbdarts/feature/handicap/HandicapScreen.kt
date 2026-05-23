package com.rbdarts.feature.handicap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbdarts.core.designsystem.RBDartsErrorState
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun HandicapScreen(
    modifier: Modifier = Modifier,
    viewModel: HandicapViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val selected = state.selectedPlayer

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RBDartsSectionHeader(
            title = "Handicaps",
            supportingText = "Review derived handicap values and apply player-level overrides when permitted."
        )
        state.players.forEach { player ->
            RBDartsSecondaryAction(
                label = "${player.playerName}: handicap ${player.derivedHandicap}",
                onClick = { viewModel.selectPlayer(player.playerName) }
            )
        }
        HandicapImpactPreview(player = selected)
        OutlinedTextField(
            value = state.overrideText,
            onValueChange = viewModel::updateOverride,
            label = { Text("Override handicap") },
            enabled = selected.canEdit,
            singleLine = true
        )
        state.message?.let { message ->
            if (selected.canEdit) {
                RBDartsInfoCard(title = "Handicap update", body = message)
            } else {
                RBDartsErrorState(title = "Permission required", body = message)
            }
        }
        RBDartsPrimaryAction("Save handicap", onClick = viewModel::saveOverride, enabled = selected.canEdit)
    }
}
