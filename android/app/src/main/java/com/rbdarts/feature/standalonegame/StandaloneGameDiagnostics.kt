package com.rbdarts.feature.standalonegame

import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.DiagnosticsReporter
import com.rbdarts.core.observability.PrivacySafeDiagnostics

class StandaloneGameDiagnostics(
    private val reporter: DiagnosticsReporter = PrivacySafeDiagnostics()
) {
    fun recordInvalidScore(inning: Int) {
        reporter.record(
            DiagnosticEvent(
                name = "standalone_invalid_score",
                severity = DiagnosticSeverity.Warning,
                attributes = mapOf("inning" to "$inning")
            )
        )
    }

    fun recordScoreAccepted(inning: Int) {
        reporter.record(
            DiagnosticEvent(
                name = "standalone_score_accepted",
                severity = DiagnosticSeverity.Debug,
                attributes = mapOf("inning" to "$inning")
            )
        )
    }

    fun recordRecovery() {
        reporter.record(DiagnosticEvent(name = "standalone_recovery", severity = DiagnosticSeverity.Info))
    }

    fun recordCompletedGame() {
        reporter.record(DiagnosticEvent(name = "standalone_completed", severity = DiagnosticSeverity.Info))
    }
}
