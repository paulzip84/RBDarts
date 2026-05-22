package com.rbdarts.feature.corrections

import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.DiagnosticsReporter
import com.rbdarts.core.observability.PrivacySafeDiagnostics

class CorrectionDiagnostics(private val reporter: DiagnosticsReporter = PrivacySafeDiagnostics()) {
    fun recordRequested(gameId: String) {
        reporter.record(DiagnosticEvent("correction_requested", DiagnosticSeverity.Info, mapOf("gameId" to gameId)))
    }

    fun recordDenied(gameId: String) {
        reporter.record(DiagnosticEvent("correction_denied", DiagnosticSeverity.Warning, mapOf("gameId" to gameId)))
    }

    fun recordRecalculationFailure(gameId: String) {
        reporter.record(DiagnosticEvent("correction_recalculation_failed", DiagnosticSeverity.Error, mapOf("gameId" to gameId)))
    }
}
