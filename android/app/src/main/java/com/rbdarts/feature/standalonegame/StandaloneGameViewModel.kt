package com.rbdarts.feature.standalonegame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbdarts.core.data.InMemoryRBDartsRepository
import com.rbdarts.core.domain.GameSummary
import com.rbdarts.core.domain.ScoringRules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StandaloneGameUiState(
    val playerNames: List<String> = listOf("Player 1", "Player 2"),
    val gameName: String = "",
    val currentInning: Int = 1,
    val session: ActiveScoreSession? = null,
    val summary: GameSummary? = null,
    val errorMessage: String? = null
) {
    val liveSummary: GameSummary?
        get() = session?.let {
            ScoringRules.summarizeStandalone(
                participants = it.participants,
                regulationInnings = it.game.regulationInnings,
                extraInningsEnabled = it.game.extraInningsEnabled
            )
        }
}

class StandaloneGameViewModel(
    private val service: StandaloneGameService = StandaloneGameService(InMemoryRBDartsRepository()),
    private val diagnostics: StandaloneGameDiagnostics = StandaloneGameDiagnostics()
) : ViewModel(), StandaloneScoreEntryActions {
    private val _state = MutableStateFlow(StandaloneGameUiState())
    val state: StateFlow<StandaloneGameUiState> = _state

    init {
        viewModelScope.launch {
            service.recoverMostRecentSession()?.let { session ->
                diagnostics.recordRecovery()
                _state.update { it.copy(session = session) }
            }
        }
    }

    fun updateGameName(gameName: String) {
        _state.update { it.copy(gameName = gameName) }
    }

    fun updatePlayerName(index: Int, name: String) {
        _state.update { state ->
            state.copy(playerNames = state.playerNames.toMutableList().also { it[index] = name })
        }
    }

    fun addPlayer() {
        _state.update { it.copy(playerNames = it.playerNames + "Player ${it.playerNames.size + 1}") }
    }

    fun startGame() {
        viewModelScope.launch {
            runCatching {
                service.createGame(
                    name = state.value.gameName,
                    createdByUserId = "local-user",
                    participantNames = state.value.playerNames
                )
            }.onSuccess { session ->
                _state.update { it.copy(session = session, currentInning = 1, summary = null, errorMessage = null) }
            }.onFailure { error ->
                _state.update { it.copy(errorMessage = error.message) }
            }
        }
    }

    override fun enterScore(score: Int, participantId: String) {
        val session = state.value.session ?: return
        viewModelScope.launch {
            runCatching {
                service.enterScore(score, participantId, state.value.currentInning, session)
            }.onSuccess { updated ->
                diagnostics.recordScoreAccepted(state.value.currentInning)
                _state.update { it.copy(session = updated, errorMessage = null) }
            }.onFailure { error ->
                diagnostics.recordInvalidScore(state.value.currentInning)
                _state.update { it.copy(errorMessage = error.message) }
            }
        }
    }

    override fun undo(participantId: String) {
        val session = state.value.session ?: return
        viewModelScope.launch {
            _state.update {
                it.copy(session = service.undoScore(participantId, state.value.currentInning, session))
            }
        }
    }

    override fun advanceInning() {
        _state.update { it.copy(currentInning = it.currentInning + 1) }
    }

    override fun completeGame() {
        val session = state.value.session ?: return
        viewModelScope.launch {
            runCatching { service.complete(session) }
                .onSuccess { summary ->
                    diagnostics.recordCompletedGame()
                    _state.update { it.copy(summary = summary, errorMessage = null) }
                }
                .onFailure { error -> _state.update { it.copy(errorMessage = error.message) } }
        }
    }
}
