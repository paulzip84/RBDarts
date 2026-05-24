package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.observability.UiDiagnosticNames
import com.rbdarts.core.observability.UiDiagnostics
import org.junit.Test

class UiDiagnosticsTest {
    @Test
    fun diagnosticsRedactSensitiveAttributes() {
        val event = UiDiagnostics.event(
            UiDiagnosticNames.AuthAttempted,
            attributes = mapOf("provider" to "Google", "email" to "player@example.com", "token" to "secret")
        )

        assertThat(event.attributes).containsEntry("provider", "Google")
        assertThat(event.attributes).doesNotContainKey("email")
        assertThat(event.attributes).doesNotContainKey("token")
    }
}
