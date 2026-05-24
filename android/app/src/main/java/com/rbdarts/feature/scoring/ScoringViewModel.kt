package com.rbdarts.feature.scoring

import androidx.lifecycle.ViewModel
import com.rbdarts.core.ui.ScoreEntryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScoringViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScoringScreenState())
    val uiState: StateFlow<ScoringScreenState> = _uiState.asStateFlow()

    fun appendDigit(digit: Int) {
        val entry = _uiState.value.scoreEntry
        if (entry.isComplete) return
        _uiState.value = _uiState.value.copy(
            scoreEntry = entry.copy(
                pendingScore = (entry.pendingScore + digit.toString()).take(2),
                errorMessage = null
            ),
            saveFailureMessage = null
        )
    }

    fun clearPendingScore() {
        _uiState.value = _uiState.value.copy(scoreEntry = _uiState.value.scoreEntry.copy(pendingScore = "", errorMessage = null))
    }

    fun submitScore() {
        val entry = _uiState.value.scoreEntry
        val score = entry.pendingScore.toIntOrNull()
        if (score == null || score !in 0..9) {
            _uiState.value = _uiState.value.copy(
                scoreEntry = entry.copy(errorMessage = "Score must be between 0 and 9.")
            )
            return
        }
        _uiState.value = _uiState.value.copy(scoreEntry = entry.withSubmittedScore(score))
    }

    fun recoverSampleGame() {
        _uiState.value = ScoringScreenState(
            scoreEntry = ScoreEntryUiState(
                inning = 2,
                target = 2,
                activeParticipant = "Morgan",
                entries = mapOf("Player 1" to listOf(4), "Morgan" to listOf(3)),
                isRecovered = true
            )
        )
    }

    fun completeGame() {
        _uiState.value = _uiState.value.copy(scoreEntry = _uiState.value.scoreEntry.copy(isComplete = true))
    }
}

fun isValidScoreInput(value: String): Boolean = value.toIntOrNull() in 0..9
