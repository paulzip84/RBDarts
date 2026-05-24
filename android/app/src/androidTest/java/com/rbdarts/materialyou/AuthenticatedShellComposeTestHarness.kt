package com.rbdarts.materialyou

import androidx.compose.runtime.Composable
import com.rbdarts.app.RBDartsAppShell
import com.rbdarts.app.RBDartsRoute

object AuthenticatedShellComposeTestHarness {
    @Composable
    fun Shell(
        currentRoute: RBDartsRoute = RBDartsRoute.Home,
        onDestinationSelected: (RBDartsRoute) -> Unit = {},
        onSignOut: () -> Unit = {}
    ) {
        RBDartsComposeTestHarness.Surface {
            RBDartsAppShell(
                currentRoute = currentRoute,
                configuration = RBDartsComposeTestHarness.configuration,
                onDestinationSelected = onDestinationSelected,
                onSignOut = onSignOut
            )
        }
    }
}
