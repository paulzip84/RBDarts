package com.rbdarts.core.ui

import kotlin.math.max
import kotlin.math.roundToInt

data class LaunchPresentationState(
    val appName: String = "RBDarts",
    val versionName: String,
    val buildNumber: String,
    val isReady: Boolean = false,
    val loadingMessage: String = "Preparing secure darts session",
    val artwork: LoadArtworkAsset = LoadArtworkAsset()
) {
    val versionLabel: String =
        if (versionName.isBlank()) {
            "Version unavailable"
        } else if (buildNumber.isBlank()) {
            "Version $versionName"
        } else {
            "Version $versionName ($buildNumber)"
        }

    val screenDescription: String = "$appName loading screen"

    val safeLoadingMessage: String = loadingMessage.toSafeLoadingMessage()
}

enum class LoadArtworkMode(val diagnosticValue: String) {
    Image("image"),
    Fallback("fallback"),
    Missing("missing")
}

data class LoadArtworkAsset(
    val sourceResourceName: String = "rbdarts_load_background",
    val fallbackResourceName: String = "rbdarts_loading_mark",
    val mode: LoadArtworkMode = LoadArtworkMode.Image,
    val sourceDimensions: String = "1280 x 2856",
    val runtimeDimensions: String = "806 x 1800",
    val format: String = "jpeg",
    val focalRegion: String = "center portrait crop preserving dartboard, red flights, and mascot",
    val contentDescription: String = "RBDarts darts artwork"
) {
    val usesPrimaryImage: Boolean = mode == LoadArtworkMode.Image
}

private fun String.toSafeLoadingMessage(): String {
    val trimmed = trim()
    val unsafeTerms = listOf(
        "token",
        "password",
        "provider payload",
        "raw provider",
        "sessionid",
        "session id",
        "stack trace",
        "exception",
        "email"
    )
    return when {
        trimmed.isBlank() -> "Preparing secure darts session"
        unsafeTerms.any { term -> trimmed.contains(term, ignoreCase = true) } -> "Preparing secure darts session"
        else -> trimmed
    }
}

enum class AuthFlowStatus { Idle, Loading, Authenticated, Cancelled, Failed, Unavailable, Offline, SessionExpired }

data class AuthUiState(
    val status: AuthFlowStatus = AuthFlowStatus.Idle,
    val selectedProvider: String? = null,
    val displayName: String? = null,
    val errorMessage: String? = null
) {
    val isLoading: Boolean = status == AuthFlowStatus.Loading
    val isAuthenticated: Boolean = status == AuthFlowStatus.Authenticated

    fun canSubmit(providerName: String): Boolean =
        !isLoading && providerName.isNotBlank()
}

enum class BaseballDartsGameType(val label: String, val supportingText: String) {
    Standalone("Standalone game", "Fast scoring for a casual board."),
    Season("Season match", "Use saved players, handicap settings, and standings."),
    Practice("Practice mode", "Track repeated attempts without affecting standings.")
}

enum class AuthenticatedNavigationAction {
    Destination,
    SignOut
}

data class AuthenticatedNavigationMenuItem(
    val id: String,
    val label: String,
    val action: AuthenticatedNavigationAction = AuthenticatedNavigationAction.Destination,
    val selected: Boolean = false,
    val enabled: Boolean = true
) {
    val safeDiagnosticDestination: String =
        when (id.lowercase()) {
            "home" -> "home"
            "gametype", "game_type", "game type" -> "game_type"
            "players" -> "players"
            "seasons" -> "seasons"
            "handicaps" -> "handicaps"
            "scoring" -> "scoring"
            "settings" -> "settings"
            "login", "signout", "sign_out" -> "login"
            else -> "home"
        }
}

data class AuthenticatedShellPresentationState(
    val currentDestination: String = "home",
    val drawerOpen: Boolean = false,
    val menuItems: List<AuthenticatedNavigationMenuItem> = emptyList()
) {
    val selectedItem: AuthenticatedNavigationMenuItem? =
        menuItems.firstOrNull { it.selected }

    val hasBottomNavigation: Boolean = false
    val hasNavigationRail: Boolean = false
}

data class GameTypeOption(
    val type: BaseballDartsGameType,
    val selected: Boolean = false,
    val enabled: Boolean = true
)

data class PlayerProfileDraft(
    val displayName: String = "",
    val nickname: String = "",
    val seedAverageText: String = ""
) {
    val seedAverage: Int? = seedAverageText.toIntOrNull()

    val validationErrors: List<String>
        get() = buildList {
            if (displayName.trim().length < 2) add("Player name must be at least 2 characters.")
            if (seedAverageText.isNotBlank() && seedAverage == null) add("Average must be a whole number.")
            if ((seedAverage ?: 0) < 0) add("Average cannot be negative.")
        }

    val canSave: Boolean = validationErrors.isEmpty()
}

data class SeasonDraft(
    val name: String = "",
    val startsOn: String = "",
    val endsOn: String = "",
    val rulesSummary: String = "Baseball darts, 9 innings"
) {
    val validationErrors: List<String>
        get() = buildList {
            if (name.trim().length < 3) add("Season name must be at least 3 characters.")
            if (startsOn.isBlank()) add("Start date is required.")
            if (endsOn.isBlank()) add("End date is required.")
        }

    val canSave: Boolean = validationErrors.isEmpty()
}

data class PlayerHandicapState(
    val playerName: String,
    val recentAverage: Int,
    val overrideHandicap: Int? = null,
    val canEdit: Boolean = true
) {
    val derivedHandicap: Int = overrideHandicap ?: max(0, (45 - recentAverage) / 3)
    val projectedScoreWithHandicap: Int = recentAverage + derivedHandicap

    fun withOverride(value: Int?): PlayerHandicapState =
        copy(overrideHandicap = value?.coerceIn(0, 99))
}

data class ScoringParticipant(
    val name: String,
    val handicap: Int = 0
)

data class ScoreEntryUiState(
    val inning: Int = 1,
    val target: Int = 1,
    val activeParticipant: String = "Player 1",
    val participants: List<ScoringParticipant> = listOf(
        ScoringParticipant("Player 1", handicap = 3),
        ScoringParticipant("Player 2", handicap = 0)
    ),
    val entries: Map<String, List<Int>> = emptyMap(),
    val pendingScore: String = "",
    val errorMessage: String? = null,
    val isRecovered: Boolean = false,
    val isComplete: Boolean = false
) {
    val pendingScoreValue: Int? = pendingScore.toIntOrNull()
    val canSubmit: Boolean = pendingScoreValue in 0..9 && !isComplete
    val averageRawScore: Int = if (entries.isEmpty()) 0 else entries.values.flatten().average().roundToInt()
    val remainingInnings: Int = (9 - inning).coerceAtLeast(0)
    val leaderName: String?
        get() {
            val totals = participants.associate { it.name to adjustedTotalFor(it.name) }
            val highest = totals.values.maxOrNull() ?: return null
            val leaders = totals.filterValues { it == highest }.keys
            return leaders.singleOrNull()
        }
    val leadMargin: Int
        get() {
            val totals = participants.map { adjustedTotalFor(it.name) }.sortedDescending()
            return if (leaderName != null && totals.size > 1) totals[0] - totals[1] else 0
        }

    fun rawTotalFor(playerName: String): Int = entries[playerName].orEmpty().sum()

    fun adjustedTotalFor(playerName: String): Int {
        val handicap = participants.firstOrNull { it.name == playerName }?.handicap ?: 0
        return rawTotalFor(playerName) + handicap
    }

    fun withSubmittedScore(score: Int): ScoreEntryUiState {
        if (score !in 0..9) {
            return copy(errorMessage = "Score must be between 0 and 9.")
        }
        val updatedEntries = entries + (activeParticipant to entries[activeParticipant].orEmpty().plus(score))
        val nextIndex = participants.indexOfFirst { it.name == activeParticipant }.let { if (it == -1) 0 else it }
        val nextParticipant = participants[(nextIndex + 1) % participants.size].name
        val completedRound = nextParticipant == participants.first().name
        val participantTotals = participants.associate { participant ->
            participant.name to updatedEntries[participant.name].orEmpty().sum() + participant.handicap
        }
        val topScore = participantTotals.values.maxOrNull() ?: 0
        val tied = participantTotals.count { it.value == topScore } > 1
        val regulationComplete = participants.all { updatedEntries[it.name].orEmpty().size >= 9 }
        val extraInningComplete = inning > 9 && completedRound
        val gameComplete = (regulationComplete && !tied) || (extraInningComplete && !tied)
        val nextInning = if (completedRound && !gameComplete) inning + 1 else inning
        return copy(
            inning = nextInning,
            target = targetForBaseballInning(nextInning),
            activeParticipant = nextParticipant,
            entries = updatedEntries,
            pendingScore = "",
            errorMessage = null,
            isComplete = gameComplete
        )
    }

    private fun targetForBaseballInning(inningNumber: Int): Int =
        when {
            inningNumber <= 0 -> 1
            inningNumber <= 20 -> inningNumber
            else -> 20
        }
}
