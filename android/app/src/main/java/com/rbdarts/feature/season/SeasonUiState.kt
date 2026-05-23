package com.rbdarts.feature.season

import com.rbdarts.core.ui.SeasonDraft

data class CreatedSeasonUiState(
    val id: String,
    val name: String,
    val startsOn: String,
    val endsOn: String,
    val rulesSummary: String
)

data class SeasonScreenState(
    val draft: SeasonDraft = SeasonDraft(),
    val seasons: List<CreatedSeasonUiState> = listOf(
        CreatedSeasonUiState("spring-2026", "Spring 2026", "2026-03-01", "2026-05-31", "Baseball darts, 9 innings")
    ),
    val message: String? = null
)
