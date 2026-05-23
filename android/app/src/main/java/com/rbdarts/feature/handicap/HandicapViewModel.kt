package com.rbdarts.feature.handicap

import androidx.lifecycle.ViewModel
import com.rbdarts.core.ui.PlayerHandicapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HandicapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HandicapScreenState())
    val uiState: StateFlow<HandicapScreenState> = _uiState.asStateFlow()

    fun selectPlayer(playerName: String) {
        val current = _uiState.value
        val selected = current.players.firstOrNull { it.playerName == playerName } ?: return
        _uiState.value = current.copy(
            selectedPlayerName = selected.playerName,
            overrideText = selected.overrideHandicap?.toString().orEmpty(),
            message = null
        )
    }

    fun updateOverride(value: String) {
        _uiState.value = _uiState.value.copy(overrideText = value.filter { it.isDigit() }.take(2), message = null)
    }

    fun saveOverride() {
        val current = _uiState.value
        val selected = current.selectedPlayer
        if (!selected.canEdit) {
            _uiState.value = current.copy(message = "You can view this player's handicap, but cannot edit it.")
            return
        }
        val overrideValue = current.overrideText.toIntOrNull()
        val updatedPlayers = current.players.map { player ->
            if (player.playerName == selected.playerName) player.withOverride(overrideValue) else player
        }
        _uiState.value = current.copy(
            players = updatedPlayers,
            message = "${selected.playerName}'s handicap is ready for game setup."
        )
    }
}

fun canEditHandicap(state: PlayerHandicapState): Boolean = state.canEdit
