package com.rbdarts.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbdarts.core.designsystem.RBDartsTopAppBar
import com.rbdarts.core.launch.ReleaseConfiguration

@Composable
fun RBDartsAppShell(
    currentRoute: RBDartsRoute,
    configuration: ReleaseConfiguration,
    onDestinationSelected: (RBDartsRoute) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            RBDartsTopAppBar(
                title = currentRoute.label,
                actions = {
                    TextButton(onClick = onSignOut) {
                        Text("Sign out")
                    }
                }
            )
        }
    ) { outerPadding ->
        Box(Modifier.padding(outerPadding)) {
            RBDartsAdaptiveNavigation(
                currentRoute = currentRoute,
                onDestinationSelected = onDestinationSelected
            ) { innerPadding ->
                RBDartsNavHost(
                    route = currentRoute,
                    configuration = configuration,
                    onNavigate = onDestinationSelected,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
