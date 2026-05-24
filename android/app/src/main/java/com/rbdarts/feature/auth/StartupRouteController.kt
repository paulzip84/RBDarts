package com.rbdarts.feature.auth

import com.rbdarts.app.RBDartsRoute

enum class StartupRouteResult {
    NoValidSession,
    ValidSession,
    SessionExpired,
    OfflineNoSession,
    Timeout
}

class StartupRouteController {
    fun initialRoute(): RBDartsRoute = RBDartsRoute.Loading

    fun routeAfterLoading(hasSession: Boolean): RBDartsRoute =
        routeFor(
            result = if (hasSession) StartupRouteResult.ValidSession else StartupRouteResult.NoValidSession
        )

    fun routeFor(
        result: StartupRouteResult,
        preservedRouteName: String? = null
    ): RBDartsRoute = when (result) {
        StartupRouteResult.ValidSession -> preservedAuthenticatedRoute(preservedRouteName) ?: RBDartsRoute.Home
        StartupRouteResult.NoValidSession,
        StartupRouteResult.SessionExpired,
        StartupRouteResult.OfflineNoSession,
        StartupRouteResult.Timeout -> RBDartsRoute.Login
    }

    fun routeAfterSignOut(): RBDartsRoute = RBDartsRoute.Login

    private fun preservedAuthenticatedRoute(routeName: String?): RBDartsRoute? {
        if (routeName.isNullOrBlank()) return null
        return RBDartsRoute.entries.firstOrNull { route ->
            route.name == routeName && route.requiresAuthentication && route.showInNavigation
        }
    }
}
