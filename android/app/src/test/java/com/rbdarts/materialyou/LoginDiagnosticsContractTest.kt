package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.observability.UiDiagnosticNames
import com.rbdarts.core.observability.UiDiagnostics
import org.junit.Test

class LoginDiagnosticsContractTest {
    @Test
    fun loginDiagnosticsUseAllowedCoarseAttributesOnly() {
        val event = UiDiagnostics.event(
            UiDiagnosticNames.LoginProviderFailed,
            attributes = mapOf(
                "provider" to "google",
                "reasonCode" to "offline",
                "token" to "raw-token",
                "rawProviderResponse" to "private"
            )
        )

        assertThat(event.attributes).containsEntry("provider", "google")
        assertThat(event.attributes).containsEntry("reasonCode", "offline")
        assertThat(event.attributes).doesNotContainKey("token")
        assertThat(event.attributes).doesNotContainKey("rawProviderResponse")
    }
}
