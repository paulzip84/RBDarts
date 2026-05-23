package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.observability.DiagnosticSeverity
import com.rbdarts.core.observability.UiDiagnosticNames
import com.rbdarts.core.observability.UiDiagnostics
import org.junit.Test

class LoadScreenDiagnosticsContractTest {
    @Test
    fun loadScreenViewedUsesAllowedAssetModeOnly() {
        val event = UiDiagnostics.loadScreenViewed(assetMode = "image")

        assertThat(event.name).isEqualTo(UiDiagnosticNames.LoadScreenViewed)
        assertThat(event.attributes).containsExactly("assetMode", "image")
    }

    @Test
    fun loadRouteCompletedUsesCoarseRouteAndTiming() {
        val event = UiDiagnostics.loadRouteCompleted(routeResult = "login", timingBucket = "lt_1s")

        assertThat(event.name).isEqualTo(UiDiagnosticNames.LoadRouteCompleted)
        assertThat(event.attributes).containsEntry("routeResult", "login")
        assertThat(event.attributes).containsEntry("timingBucket", "lt_1s")
    }

    @Test
    fun loadRouteDelayedIsWarningWithTimeoutOnly() {
        val event = UiDiagnostics.loadRouteDelayed(timingBucket = "gt_2s")

        assertThat(event.name).isEqualTo(UiDiagnosticNames.LoadRouteDelayed)
        assertThat(event.severity).isEqualTo(DiagnosticSeverity.Warning)
        assertThat(event.attributes).containsEntry("routeResult", "timeout")
    }

    @Test
    fun loadDiagnosticsRedactDisallowedAttributes() {
        val event = UiDiagnostics.event(
            UiDiagnosticNames.LoadRouteCompleted,
            attributes = mapOf(
                "routeResult" to "login",
                "timingBucket" to "lt_1s",
                "token" to "abc",
                "email" to "player@example.com",
                "sessionId" to "session-123",
                "debugLog" to "stack"
            )
        )

        assertThat(event.attributes).containsEntry("routeResult", "login")
        assertThat(event.attributes).doesNotContainKey("token")
        assertThat(event.attributes).doesNotContainKey("email")
        assertThat(event.attributes).doesNotContainKey("sessionId")
        assertThat(event.attributes).doesNotContainKey("debugLog")
    }
}
