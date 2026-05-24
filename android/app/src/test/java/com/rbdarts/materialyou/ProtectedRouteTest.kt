package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.app.protectedRouteOrLogin
import org.junit.Test

class ProtectedRouteTest {
    @Test
    fun protectedRouteRedirectsToLoginWhenSignedOut() {
        assertThat(protectedRouteOrLogin(RBDartsRoute.Handicaps, isAuthenticated = false))
            .isEqualTo(RBDartsRoute.Login)
    }

    @Test
    fun everyDrawerDestinationRequiresAuthentication() {
        RBDartsRoute.entries
            .filter { it.showInNavigation }
            .forEach { route ->
                assertThat(protectedRouteOrLogin(route, isAuthenticated = false))
                    .isEqualTo(RBDartsRoute.Login)
            }
    }

    @Test
    fun authenticatedDrawerDestinationRemainsAvailable() {
        assertThat(protectedRouteOrLogin(RBDartsRoute.Scoring, isAuthenticated = true))
            .isEqualTo(RBDartsRoute.Scoring)
    }
}
