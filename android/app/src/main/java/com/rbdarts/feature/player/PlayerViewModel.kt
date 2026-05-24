package com.rbdarts.feature.player

import androidx.lifecycle.ViewModel
import com.rbdarts.core.ui.PlayerProfileDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlayerScreenState())
    val uiState: StateFlow<PlayerScreenState> = _uiState.asStateFlow()

    fun updateDisplayName(value: String) {
        updateDraft { copy(displayName = value) }
    }

    fun updateNickname(value: String) {
        updateDraft { copy(nickname = value) }
    }

    fun updateSeedAverage(value: String) {
        updateDraft { copy(seedAverageText = value.filter { it.isDigit() }.take(3)) }
    }

    fun savePlayer() {
        val current = _uiState.value
        if (!current.draft.canSave) {
            _uiState.value = current.copy(message = current.draft.validationErrors.joinToString(" "))
            return
        }
        val saved = CreatedPlayerUiState(
            id = "player-${current.players.size + 1}",
            displayName = current.draft.displayName.trim(),
            nickname = current.draft.nickname.trim(),
            seedAverage = current.draft.seedAverage ?: 0
        )
        _uiState.value = current.copy(
            draft = PlayerProfileDraft(),
            players = current.players + saved,
            message = "${saved.displayName} is ready for scoring."
        )
    }

    private fun updateDraft(block: PlayerProfileDraft.() -> PlayerProfileDraft) {
        val current = _uiState.value
        _uiState.value = current.copy(draft = current.draft.block(), message = null)
    }
}
