package com.rbdarts.core.observability

enum class DiagnosticSeverity { Debug, Info, Warning, Error }

data class DiagnosticEvent(
    val name: String,
    val severity: DiagnosticSeverity,
    val attributes: Map<String, String> = emptyMap()
)

object DiagnosticEventNames {
    const val ScoreEntryRejected = "score_entry_rejected"
    const val CorrectionRequested = "correction_requested"
    const val CorrectionRejected = "correction_rejected"
    const val CorrectionApplied = "correction_applied"
    const val GameLocked = "game_locked"
    const val GameUnlockRequested = "game_unlock_requested"
    const val SubstitutionApplied = "substitution_applied"
    const val BaseballStatsViewed = "baseball_stats_viewed"
    const val BaseballProjectionUnavailable = "baseball_projection_unavailable"
}

interface DiagnosticsReporter {
    fun record(event: DiagnosticEvent)
}

class PrivacySafeDiagnostics : DiagnosticsReporter {
    private val redactedKeys = listOf("token", "secret", "password", "email", "reason", "providerUserId")
    val events: MutableList<DiagnosticEvent> = mutableListOf()

    override fun record(event: DiagnosticEvent) {
        events += event.copy(
            attributes = event.attributes.filterKeys { key ->
                redactedKeys.none { forbidden -> key.contains(forbidden, ignoreCase = true) }
            }
        )
    }
}
