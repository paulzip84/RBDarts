package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.observability.UiDiagnosticNames
import com.rbdarts.core.observability.UiDiagnostics
import org.junit.Test

class NavigationDiagnosticsContractTest {
    @Test
    fun drawerDiagnosticsUseAllowedEventNamesAndCoarseDestinations() {
        val event = UiDiagnostics.navDestinationSelected("GameType")

        assertThat(event.name).isEqualTo(UiDiagnosticNames.NavDestinationSelected)
        assertThat(event.attributes).containsEntry("destination", "game_type")
        assertThat(event.attributes).containsEntry("result", "selected")
        assertThat(event.attributes).doesNotContainKey("email")
        assertThat(event.attributes).doesNotContainKey("sessionId")
    }

    @Test
    fun signOutDiagnosticRoutesToLoginWithoutSessionData() {
        val event = UiDiagnostics.navSignOutSelected()

        assertThat(event.name).isEqualTo(UiDiagnosticNames.NavSignOutSelected)
        assertThat(event.attributes).containsEntry("destination", "login")
        assertThat(event.attributes).containsEntry("result", "sign_out")
        assertThat(event.attributes.keys).containsNoneOf("token", "providerUserId", "rawProviderResponse", "sessionId")
    }
}
