package com.rbdarts.feature.scoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbdarts.core.designsystem.RBDartsErrorState
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader

@Composable
fun ScoringScreen(
    modifier: Modifier = Modifier,
    viewModel: ScoringViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scoreEntry = state.scoreEntry

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RBDartsSectionHeader(
            title = "Scoring",
            supportingText = "Enter baseball darts scores with validation, running totals, handicap impact, and recovery state."
        )
        ScoringContextHeader(state = scoreEntry)
        scoreEntry.errorMessage?.let {
            RBDartsErrorState(title = "Invalid score", body = it)
        }
        state.saveFailureMessage?.let {
            RBDartsErrorState(title = "Save failed", body = it)
        }
        ScoreEntryControls(
            pendingScore = scoreEntry.pendingScore,
            canSubmit = scoreEntry.canSubmit,
            onDigit = viewModel::appendDigit,
            onClear = viewModel::clearPendingScore,
            onSubmit = viewModel::submitScore
        )
        ScoreTotalsPanel(state = scoreEntry)
        ScorecardReviewPanel(state = scoreEntry)
        RBDartsSecondaryAction("Recover sample game", onClick = viewModel::recoverSampleGame)
        RBDartsPrimaryAction("Complete game", onClick = viewModel::completeGame)
    }
}
