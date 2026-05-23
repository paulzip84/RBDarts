package com.rbdarts.feature.season

import androidx.lifecycle.ViewModel
import com.rbdarts.core.ui.SeasonDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SeasonViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SeasonScreenState())
    val uiState: StateFlow<SeasonScreenState> = _uiState.asStateFlow()

    fun updateName(value: String) {
        updateDraft { copy(name = value) }
    }

    fun updateStartsOn(value: String) {
        updateDraft { copy(startsOn = value.take(10)) }
    }

    fun updateEndsOn(value: String) {
        updateDraft { copy(endsOn = value.take(10)) }
    }

    fun updateRules(value: String) {
        updateDraft { copy(rulesSummary = value) }
    }

    fun saveSeason() {
        val current = _uiState.value
        if (!current.draft.canSave) {
            _uiState.value = current.copy(message = current.draft.validationErrors.joinToString(" "))
            return
        }
        val saved = CreatedSeasonUiState(
            id = "season-${current.seasons.size + 1}",
            name = current.draft.name.trim(),
            startsOn = current.draft.startsOn,
            endsOn = current.draft.endsOn,
            rulesSummary = current.draft.rulesSummary
        )
        _uiState.value = current.copy(
            draft = SeasonDraft(),
            seasons = current.seasons + saved,
            message = "${saved.name} season is ready."
        )
    }

    private fun updateDraft(block: SeasonDraft.() -> SeasonDraft) {
        val current = _uiState.value
        _uiState.value = current.copy(draft = current.draft.block(), message = null)
    }
}
