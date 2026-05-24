package com.rbdarts.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.rbdarts.R
import com.rbdarts.core.designsystem.RBDartsAuthenticatedBackground
import com.rbdarts.core.designsystem.RBDartsAuthenticatedTheme
import com.rbdarts.core.designsystem.RBDartsTopAppBar
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.core.observability.UiDiagnostics
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RBDartsAppShell(
    currentRoute: RBDartsRoute,
    configuration: ReleaseConfiguration,
    onDestinationSelected: (RBDartsRoute) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    RBDartsAuthenticatedTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        fun closeDrawer(result: String = "dismissed") {
            scope.launch {
                drawerState.close()
                UiDiagnostics.navMenuClosed(result)
            }
        }

        BackHandler(enabled = drawerState.isOpen) {
            closeDrawer()
        }

        RBDartsAdaptiveNavigation(
            currentRoute = currentRoute,
            drawerState = drawerState,
            onDestinationSelected = { destination ->
                scope.launch {
                    drawerState.close()
                    UiDiagnostics.navDestinationSelected(destination.name)
                    onDestinationSelected(destination)
                }
            },
            onSignOut = {
                scope.launch {
                    drawerState.close()
                    UiDiagnostics.navSignOutSelected()
                    onSignOut()
                }
            },
            modifier = modifier
        ) { drawerPadding ->
            Scaffold(
                topBar = {
                    RBDartsTopAppBar(
                        title = currentRoute.label,
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                        UiDiagnostics.navMenuOpened()
                                    }
                                },
                                modifier = Modifier
                                    .testTag("hamburger_nav_button")
                                    .semantics { contentDescription = "Open navigation menu" }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_rbdarts_menu),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                containerColor = androidx.compose.ui.graphics.Color.Transparent
            ) { outerPadding ->
                RBDartsAuthenticatedBackground {
                    Box(
                        Modifier
                            .padding(drawerPadding)
                            .padding(outerPadding)
                    ) {
                        RBDartsNavHost(
                            route = currentRoute,
                            configuration = configuration,
                            onNavigate = onDestinationSelected
                        )
                    }
                }
            }
        }
    }
}
