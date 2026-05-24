package com.rbdarts.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.feature.gametype.GameTypeScreen
import com.rbdarts.feature.handicap.HandicapScreen
import com.rbdarts.feature.home.HomeScreen
import com.rbdarts.feature.player.PlayerScreen
import com.rbdarts.feature.scoring.ScoringScreen
import com.rbdarts.feature.season.SeasonScreen
import com.rbdarts.feature.settings.PrivacyAndSupportScreen

@Composable
fun RBDartsNavHost(
    route: RBDartsRoute,
    configuration: ReleaseConfiguration,
    onNavigate: (RBDartsRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    when (route) {
        RBDartsRoute.Home -> HomeScreen(onNavigate = onNavigate, modifier = modifier)
        RBDartsRoute.GameType -> GameTypeScreen(onNavigate = onNavigate, modifier = modifier)
        RBDartsRoute.Players -> PlayerScreen(modifier = modifier)
        RBDartsRoute.Seasons -> SeasonScreen(modifier = modifier)
        RBDartsRoute.Handicaps -> HandicapScreen(modifier = modifier)
        RBDartsRoute.Scoring -> ScoringScreen(modifier = modifier)
        RBDartsRoute.Settings -> PrivacyAndSupportScreen(configuration = configuration, modifier = modifier)
        RBDartsRoute.Loading, RBDartsRoute.Login -> HomeScreen(onNavigate = onNavigate, modifier = modifier)
    }
}

fun protectedRouteOrLogin(route: RBDartsRoute, isAuthenticated: Boolean): RBDartsRoute =
    RBDartsDestinations.destinationFor(route, isAuthenticated)
