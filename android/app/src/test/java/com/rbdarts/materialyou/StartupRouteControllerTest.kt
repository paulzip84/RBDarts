package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.feature.auth.StartupRouteController
import com.rbdarts.feature.auth.StartupRouteResult
import org.junit.Test

class StartupRouteControllerTest {
    private val controller = StartupRouteController()

    @Test
    fun unauthenticatedStartupRoutesFromLoadingToLogin() {
        assertThat(controller.initialRoute()).isEqualTo(RBDartsRoute.Loading)
        assertThat(controller.routeAfterLoading(hasSession = false)).isEqualTo(RBDartsRoute.Login)
    }

    @Test
    fun authenticatedStartupRoutesHome() {
        assertThat(controller.routeAfterLoading(hasSession = true)).isEqualTo(RBDartsRoute.Home)
    }

    @Test
    fun authenticatedStartupMayRestoreSafeProtectedRoute() {
        assertThat(
            controller.routeFor(
                result = StartupRouteResult.ValidSession,
                preservedRouteName = RBDartsRoute.Scoring.name
            )
        ).isEqualTo(RBDartsRoute.Scoring)
    }

    @Test
    fun authenticatedStartupRejectsUnsafePreservedRoute() {
        assertThat(
            controller.routeFor(
                result = StartupRouteResult.ValidSession,
                preservedRouteName = RBDartsRoute.Login.name
            )
        ).isEqualTo(RBDartsRoute.Home)
    }

    @Test
    fun sessionExpiredOfflineAndTimeoutRouteToLogin() {
        assertThat(controller.routeFor(StartupRouteResult.SessionExpired)).isEqualTo(RBDartsRoute.Login)
        assertThat(controller.routeFor(StartupRouteResult.OfflineNoSession)).isEqualTo(RBDartsRoute.Login)
        assertThat(controller.routeFor(StartupRouteResult.Timeout)).isEqualTo(RBDartsRoute.Login)
    }
}
