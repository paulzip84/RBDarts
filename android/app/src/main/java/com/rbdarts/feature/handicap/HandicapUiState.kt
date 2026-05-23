package com.rbdarts.feature.handicap

import com.rbdarts.core.ui.PlayerHandicapState

data class HandicapScreenState(
    val players: List<PlayerHandicapState> = listOf(
        PlayerHandicapState("Avery", recentAverage = 42, overrideHandicap = 2, canEdit = true),
        PlayerHandicapState("Morgan", recentAverage = 38, overrideHandicap = null, canEdit = true),
        PlayerHandicapState("Guest", recentAverage = 31, overrideHandicap = null, canEdit = false)
    ),
    val selectedPlayerName: String = "Avery",
    val overrideText: String = "2",
    val message: String? = null
) {
    val selectedPlayer: PlayerHandicapState =
        players.firstOrNull { it.playerName == selectedPlayerName } ?: players.first()
}
