package com.rbdarts.app

enum class RBDartsRoute(
    val label: String,
    val shortLabel: String,
    val requiresAuthentication: Boolean,
    val showInNavigation: Boolean
) {
    Loading("Loading", "Load", requiresAuthentication = false, showInNavigation = false),
    Login("Sign in", "Login", requiresAuthentication = false, showInNavigation = false),
    Home("Home", "Home", requiresAuthentication = true, showInNavigation = true),
    GameType("Game type", "Game", requiresAuthentication = true, showInNavigation = true),
    Players("Players", "Players", requiresAuthentication = true, showInNavigation = true),
    Seasons("Seasons", "Seasons", requiresAuthentication = true, showInNavigation = true),
    Handicaps("Handicaps", "Hcap", requiresAuthentication = true, showInNavigation = true),
    Scoring("Scoring", "Score", requiresAuthentication = true, showInNavigation = true),
    Settings("Settings", "Settings", requiresAuthentication = true, showInNavigation = true)
}

object RBDartsDestinations {
    val topLevel: List<RBDartsRoute> = RBDartsRoute.entries.filter { it.showInNavigation }

    fun isProtected(route: RBDartsRoute): Boolean = route.requiresAuthentication

    fun startupRoute(isAuthenticated: Boolean): RBDartsRoute =
        if (isAuthenticated) RBDartsRoute.Home else RBDartsRoute.Loading

    fun destinationFor(route: RBDartsRoute, isAuthenticated: Boolean): RBDartsRoute =
        if (route.requiresAuthentication && !isAuthenticated) RBDartsRoute.Login else route
}

data class AppShellState(
    val currentRoute: RBDartsRoute = RBDartsRoute.Home,
    val isAuthenticated: Boolean = true
) {
    val visibleRoute: RBDartsRoute = RBDartsDestinations.destinationFor(currentRoute, isAuthenticated)

    fun navigate(route: RBDartsRoute): AppShellState =
        copy(currentRoute = RBDartsDestinations.destinationFor(route, isAuthenticated))

    fun signIn(): AppShellState = copy(isAuthenticated = true, currentRoute = RBDartsRoute.Home)

    fun signOut(): AppShellState = copy(isAuthenticated = false, currentRoute = RBDartsRoute.Login)
}
