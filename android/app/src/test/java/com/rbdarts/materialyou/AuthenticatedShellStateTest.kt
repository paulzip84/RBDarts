package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.AuthenticatedNavigationAction
import com.rbdarts.core.ui.AuthenticatedNavigationMenuItem
import com.rbdarts.core.ui.AuthenticatedShellPresentationState
import org.junit.Test

class AuthenticatedShellStateTest {
    @Test
    fun shellStateNeverUsesBottomNavigationOrRail() {
        val state = AuthenticatedShellPresentationState(
            currentDestination = "home",
            drawerOpen = false,
            menuItems = listOf(AuthenticatedNavigationMenuItem(id = "home", label = "Home", selected = true))
        )

        assertThat(state.hasBottomNavigation).isFalse()
        assertThat(state.hasNavigationRail).isFalse()
        assertThat(state.selectedItem?.label).isEqualTo("Home")
    }

    @Test
    fun signOutMenuItemMapsToLoginDiagnosticDestination() {
        val item = AuthenticatedNavigationMenuItem(
            id = "sign_out",
            label = "Sign Out",
            action = AuthenticatedNavigationAction.SignOut
        )

        assertThat(item.safeDiagnosticDestination).isEqualTo("login")
    }
}
