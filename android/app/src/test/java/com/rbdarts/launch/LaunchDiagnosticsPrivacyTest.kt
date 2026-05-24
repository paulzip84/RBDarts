package com.rbdarts.launch

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.observability.DiagnosticEvent
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.PrivacySafeDiagnostics
import org.junit.Test

class LaunchDiagnosticsPrivacyTest {
    @Test
    fun diagnosticsDropSensitiveLaunchAttributes() {
        val diagnostics = PrivacySafeDiagnostics()

        diagnostics.record(
            DiagnosticEvent(
                name = "launch.sign_in",
                severity = DiagnosticSeverity.Info,
                attributes = mapOf(
                    "provider" to "google",
                    "email" to "tester@example.com",
                    "accessToken" to "redacted"
                )
            )
        )

        assertThat(diagnostics.events.first().attributes).containsEntry("provider", "google")
        assertThat(diagnostics.events.first().attributes).doesNotContainKey("email")
        assertThat(diagnostics.events.first().attributes).doesNotContainKey("accessToken")
    }
}
