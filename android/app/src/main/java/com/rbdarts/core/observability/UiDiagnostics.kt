package com.rbdarts.core.observability

object UiDiagnosticNames {
    const val StartupShown = "android_ui_startup_shown"
    const val LoadScreenViewed = "android_load_screen_viewed"
    const val LoadArtworkMode = "android_load_artwork_mode"
    const val LoadRouteCompleted = "android_load_route_completed"
    const val LoadRouteDelayed = "android_load_route_delayed"
    const val AuthAttempted = "android_ui_auth_attempted"
    const val AuthCompleted = "android_ui_auth_completed"
    const val AuthFailed = "android_ui_auth_failed"
    const val LoginViewed = "android_login_viewed"
    const val LoginProviderSelected = "android_login_provider_selected"
    const val LoginProviderSucceeded = "android_login_provider_succeeded"
    const val LoginProviderCancelled = "android_login_provider_cancelled"
    const val LoginProviderFailed = "android_login_provider_failed"
    const val LoginHelpSelected = "android_login_help_selected"
    const val RouteSelected = "android_ui_route_selected"
    const val NavMenuOpened = "android_nav_menu_opened"
    const val NavMenuClosed = "android_nav_menu_closed"
    const val NavDestinationSelected = "android_nav_destination_selected"
    const val NavSignOutSelected = "android_nav_sign_out_selected"
    const val NavProtectedRedirect = "android_nav_protected_redirect"
    const val HandicapEdited = "android_ui_handicap_edited"
    const val ScoringEntrySubmitted = "android_ui_score_entry_submitted"
    const val ScoringEntryRejected = "android_ui_score_entry_rejected"
}

object UiDiagnostics {
    private val redactedAttributeNames = listOf(
        "token",
        "secret",
        "password",
        "email",
        "providerUserId",
        "rawProviderResponse",
        "displayName",
        "userId",
        "sessionId",
        "account",
        "debugLog",
        "rawScore"
    )

    fun event(
        name: String,
        severity: DiagnosticSeverity = DiagnosticSeverity.Info,
        attributes: Map<String, String> = emptyMap()
    ): DiagnosticEvent = DiagnosticEvent(
        name = name,
        severity = severity,
        attributes = privacySafeAttributes(attributes)
    )

    fun startupShown(appVersion: String): DiagnosticEvent =
        event(UiDiagnosticNames.StartupShown, attributes = mapOf("appVersion" to appVersion))

    fun loadScreenViewed(assetMode: String): DiagnosticEvent =
        event(UiDiagnosticNames.LoadScreenViewed, attributes = mapOf("assetMode" to assetMode))

    fun loadArtworkMode(assetMode: String): DiagnosticEvent =
        event(UiDiagnosticNames.LoadArtworkMode, attributes = mapOf("assetMode" to assetMode))

    fun loadRouteCompleted(routeResult: String, timingBucket: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.LoadRouteCompleted,
            attributes = mapOf("routeResult" to routeResult, "timingBucket" to timingBucket)
        )

    fun loadRouteDelayed(timingBucket: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.LoadRouteDelayed,
            DiagnosticSeverity.Warning,
            mapOf("routeResult" to "timeout", "timingBucket" to timingBucket)
        )

    fun authAttempted(provider: String): DiagnosticEvent =
        event(UiDiagnosticNames.AuthAttempted, attributes = mapOf("provider" to provider))

    fun authFailed(provider: String): DiagnosticEvent =
        event(UiDiagnosticNames.AuthFailed, DiagnosticSeverity.Warning, mapOf("provider" to provider))

    fun loginViewed(): DiagnosticEvent =
        event(UiDiagnosticNames.LoginViewed)

    fun loginProviderSelected(provider: String): DiagnosticEvent =
        event(UiDiagnosticNames.LoginProviderSelected, attributes = mapOf("provider" to provider.lowercase()))

    fun loginProviderSucceeded(provider: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.LoginProviderSucceeded,
            attributes = mapOf("provider" to provider.lowercase(), "status" to "success")
        )

    fun loginProviderCancelled(provider: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.LoginProviderCancelled,
            DiagnosticSeverity.Info,
            mapOf("provider" to provider.lowercase(), "status" to "cancelled", "reasonCode" to "provider_cancelled")
        )

    fun loginProviderFailed(provider: String, reasonCode: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.LoginProviderFailed,
            DiagnosticSeverity.Warning,
            mapOf("provider" to provider.lowercase(), "status" to "failed", "reasonCode" to reasonCode)
        )

    fun loginHelpSelected(kind: String): DiagnosticEvent =
        event(UiDiagnosticNames.LoginHelpSelected, attributes = mapOf("kind" to kind))

    fun routeSelected(routeName: String): DiagnosticEvent =
        event(UiDiagnosticNames.RouteSelected, attributes = mapOf("route" to routeName))

    fun navMenuOpened(): DiagnosticEvent =
        event(UiDiagnosticNames.NavMenuOpened, attributes = mapOf("menuState" to "opened"))

    fun navMenuClosed(result: String = "dismissed"): DiagnosticEvent =
        event(UiDiagnosticNames.NavMenuClosed, attributes = mapOf("menuState" to "closed", "result" to result))

    fun navDestinationSelected(destination: String, timingBucket: String = "lt_250ms"): DiagnosticEvent =
        event(
            UiDiagnosticNames.NavDestinationSelected,
            attributes = mapOf(
                "destination" to destination.toSafeDestination(),
                "result" to "selected",
                "timingBucket" to timingBucket
            )
        )

    fun navSignOutSelected(): DiagnosticEvent =
        event(
            UiDiagnosticNames.NavSignOutSelected,
            attributes = mapOf("destination" to "login", "result" to "sign_out")
        )

    fun navProtectedRedirect(destination: String): DiagnosticEvent =
        event(
            UiDiagnosticNames.NavProtectedRedirect,
            DiagnosticSeverity.Warning,
            mapOf("destination" to destination.toSafeDestination(), "result" to "redirected")
        )

    fun handicapEdited(hasOverride: Boolean): DiagnosticEvent =
        event(UiDiagnosticNames.HandicapEdited, attributes = mapOf("override" to hasOverride.toString()))

    fun scoreEntrySubmitted(): DiagnosticEvent =
        event(UiDiagnosticNames.ScoringEntrySubmitted)

    fun scoreEntryRejected(reasonCode: String): DiagnosticEvent =
        event(UiDiagnosticNames.ScoringEntryRejected, DiagnosticSeverity.Warning, mapOf("reasonCode" to reasonCode))

    fun privacySafeAttributes(attributes: Map<String, String>): Map<String, String> =
        attributes.filterKeys { key ->
            redactedAttributeNames.none { forbidden -> key.contains(forbidden, ignoreCase = true) }
        }

    private fun String.toSafeDestination(): String =
        when (lowercase()) {
            "home" -> "home"
            "gametype", "game_type", "game type" -> "game_type"
            "players" -> "players"
            "seasons" -> "seasons"
            "handicaps" -> "handicaps"
            "scoring" -> "scoring"
            "settings" -> "settings"
            "login" -> "login"
            else -> "home"
        }
}
