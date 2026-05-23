package com.rbdarts.feature.scoring

import com.rbdarts.core.ui.ScoreEntryUiState

data class ScoringScreenState(
    val scoreEntry: ScoreEntryUiState = ScoreEntryUiState(isRecovered = true),
    val saveFailureMessage: String? = null
)
