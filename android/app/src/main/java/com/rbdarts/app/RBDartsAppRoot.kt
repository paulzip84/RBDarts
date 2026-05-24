package com.rbdarts.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.feature.auth.AuthProviderId
import com.rbdarts.feature.auth.LoadingScreen
import com.rbdarts.feature.auth.LoginScreen
import com.rbdarts.feature.auth.LoginViewModel
import com.rbdarts.feature.auth.StartupRouteController
import kotlinx.coroutines.delay

@Composable
fun RBDartsAppRoot(
    configuration: ReleaseConfiguration,
    modifier: Modifier = Modifier
) {
    val startupRouteController = remember { StartupRouteController() }
    val loginViewModel: LoginViewModel = viewModel()
    val authState by loginViewModel.uiState.collectAsState()
    var isAuthenticated by rememberSaveable { mutableStateOf(false) }
    var currentRouteName by rememberSaveable { mutableStateOf(startupRouteController.initialRoute().name) }
    val currentRoute = RBDartsRoute.valueOf(currentRouteName)

    LaunchedEffect(Unit) {
        delay(1_200)
        currentRouteName = startupRouteController.routeAfterLoading(isAuthenticated).name
    }

    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) {
            isAuthenticated = true
            currentRouteName = RBDartsRoute.Home.name
        }
    }

    when (currentRoute) {
        RBDartsRoute.Loading -> LoadingScreen(
            state = LaunchPresentationState(
                versionName = configuration.appVersion,
                buildNumber = configuration.buildNumber
            ),
            modifier = modifier
        )
        RBDartsRoute.Login -> LoginScreen(
            state = authState,
            configuration = configuration,
            onGoogleSignIn = { loginViewModel.signIn(AuthProviderId.Google) },
            onFacebookSignIn = { loginViewModel.signIn(AuthProviderId.Facebook) },
            modifier = modifier
        )
        else -> RBDartsAppShell(
            currentRoute = RBDartsDestinations.destinationFor(currentRoute, isAuthenticated),
            configuration = configuration,
            onDestinationSelected = { destination ->
                currentRouteName = RBDartsDestinations.destinationFor(destination, isAuthenticated).name
            },
            onSignOut = {
                loginViewModel.signOut()
                isAuthenticated = false
                currentRouteName = startupRouteController.routeAfterSignOut().name
            },
            modifier = modifier
        )
    }
}
