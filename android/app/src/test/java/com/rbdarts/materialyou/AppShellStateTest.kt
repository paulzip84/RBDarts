package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.AppShellState
import com.rbdarts.app.RBDartsRoute
import org.junit.Test

class AppShellStateTest {
    @Test
    fun signOutReturnsToLogin() {
        val state = AppShellState(currentRoute = RBDartsRoute.Scoring).signOut()

        assertThat(state.isAuthenticated).isFalse()
        assertThat(state.visibleRoute).isEqualTo(RBDartsRoute.Login)
    }
}
