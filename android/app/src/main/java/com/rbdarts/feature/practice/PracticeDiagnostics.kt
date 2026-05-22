package com.rbdarts.feature.practice

import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.DiagnosticsReporter
import com.rbdarts.core.observability.PrivacySafeDiagnostics

class PracticeDiagnostics(private val reporter: DiagnosticsReporter = PrivacySafeDiagnostics()) {
    fun recordInvalidPracticeScore(target: Int) {
        reporter.record(DiagnosticEvent("practice_invalid_score", DiagnosticSeverity.Warning, mapOf("target" to "$target")))
    }

    fun recordSaveFailure(target: Int) {
        reporter.record(DiagnosticEvent("practice_save_failed", DiagnosticSeverity.Error, mapOf("target" to "$target")))
    }
}
