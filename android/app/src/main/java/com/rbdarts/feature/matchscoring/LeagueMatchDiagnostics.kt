package com.rbdarts.feature.matchscoring

import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.DiagnosticsReporter
import com.rbdarts.core.observability.PrivacySafeDiagnostics

class LeagueMatchDiagnostics(
    private val reporter: DiagnosticsReporter = PrivacySafeDiagnostics()
) {
    fun recordPermissionDenied(action: String) {
        reporter.record(DiagnosticEvent("league_permission_denied", DiagnosticSeverity.Warning, mapOf("action" to action)))
    }

    fun recordLockTransition(gameId: String) {
        reporter.record(DiagnosticEvent("league_game_locked", DiagnosticSeverity.Info, mapOf("gameId" to gameId)))
    }

    fun recordFinalizeFailure(matchId: String) {
        reporter.record(DiagnosticEvent("league_finalize_failed", DiagnosticSeverity.Error, mapOf("matchId" to matchId)))
    }
}
