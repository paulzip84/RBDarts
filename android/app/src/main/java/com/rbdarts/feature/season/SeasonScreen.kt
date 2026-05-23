package com.rbdarts.feature.season

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
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun SeasonScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RBDartsSectionHeader(
            title = "Seasons",
            supportingText = "Define date windows and rule summaries before scheduling scored matches."
        )
        OutlinedTextField(value = state.draft.name, onValueChange = viewModel::updateName, label = { Text("Season name") }, singleLine = true)
        OutlinedTextField(value = state.draft.startsOn, onValueChange = viewModel::updateStartsOn, label = { Text("Start date") }, singleLine = true)
        OutlinedTextField(value = state.draft.endsOn, onValueChange = viewModel::updateEndsOn, label = { Text("End date") }, singleLine = true)
        OutlinedTextField(value = state.draft.rulesSummary, onValueChange = viewModel::updateRules, label = { Text("Rules summary") })
        state.message?.let { message ->
            if (state.draft.validationErrors.isEmpty()) {
                RBDartsInfoCard(title = "Season update", body = message)
            } else {
                RBDartsErrorState(title = "Check season details", body = message)
            }
        }
        RBDartsPrimaryAction("Save season", onClick = viewModel::saveSeason, enabled = state.draft.canSave)
        state.seasons.forEach { season ->
            RBDartsInfoCard(
                title = season.name,
                body = "${season.startsOn} to ${season.endsOn}. ${season.rulesSummary}."
            )
        }
    }
}
