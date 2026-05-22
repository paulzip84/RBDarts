package com.rbdarts.feature.stats

import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.DiagnosticsReporter
import com.rbdarts.core.observability.PrivacySafeDiagnostics

class StatsDiagnostics(private val reporter: DiagnosticsReporter = PrivacySafeDiagnostics()) {
    fun recordStatsLoadingFailure(scope: String) {
        reporter.record(DiagnosticEvent("stats_loading_failed", DiagnosticSeverity.Error, mapOf("scope" to scope)))
    }

    fun recordProjectionFailure(scope: String) {
        reporter.record(DiagnosticEvent("stats_projection_failed", DiagnosticSeverity.Warning, mapOf("scope" to scope)))
    }

    fun recordStaleOfflineData(scope: String) {
        reporter.record(DiagnosticEvent("stats_stale_offline_data", DiagnosticSeverity.Info, mapOf("scope" to scope)))
    }
}
