package com.rbdarts.feature.player

import com.rbdarts.core.ui.PlayerProfileDraft

data class CreatedPlayerUiState(
    val id: String,
    val displayName: String,
    val nickname: String,
    val seedAverage: Int
)

data class PlayerScreenState(
    val draft: PlayerProfileDraft = PlayerProfileDraft(),
    val players: List<CreatedPlayerUiState> = listOf(
        CreatedPlayerUiState("avery", "Avery", "Ace", 42),
        CreatedPlayerUiState("morgan", "Morgan", "Mo", 38)
    ),
    val message: String? = null
)
