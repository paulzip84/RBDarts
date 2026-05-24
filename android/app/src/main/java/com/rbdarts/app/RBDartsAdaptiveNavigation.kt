package com.rbdarts.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rbdarts.R
import com.rbdarts.core.designsystem.AuthenticatedShellDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RBDartsAdaptiveNavigation(
    currentRoute: RBDartsRoute,
    drawerState: DrawerState,
    onDestinationSelected: (RBDartsRoute) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            RBDartsDrawerContent(
                currentRoute = currentRoute,
                onDestinationSelected = onDestinationSelected,
                onSignOut = onSignOut
            )
        },
        content = { content(PaddingValues()) }
    )
}

@Composable
private fun RBDartsDrawerContent(
    currentRoute: RBDartsRoute,
    onDestinationSelected: (RBDartsRoute) -> Unit,
    onSignOut: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .widthIn(max = AuthenticatedShellDefaults.DrawerWidth),
        drawerContainerColor = AuthenticatedShellDefaults.Surface,
        drawerContentColor = AuthenticatedShellDefaults.Text
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 18.dp)) {
            Text(
                text = "RBDarts",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AuthenticatedShellDefaults.Text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Text(
                text = "Secure darts session",
                style = MaterialTheme.typography.bodyMedium,
                color = AuthenticatedShellDefaults.MutedText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
            Spacer(Modifier.height(16.dp))
            RBDartsDestinations.topLevel.forEach { destination ->
                RBDartsDrawerDestination(
                    route = destination,
                    selected = currentRoute == destination,
                    onClick = { onDestinationSelected(destination) }
                )
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(color = AuthenticatedShellDefaults.Outline)
            Spacer(Modifier.height(8.dp))
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Sign Out",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = false,
                onClick = onSignOut,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rbdarts_sign_out),
                        contentDescription = null
                    )
                },
                colors = drawerItemColors(),
                modifier = Modifier
                    .semantics { contentDescription = "Sign Out" }
            )
        }
    }
}

@Composable
private fun RBDartsDrawerDestination(
    route: RBDartsRoute,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = route.label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = route.drawerIconRes()),
                contentDescription = null
            )
        },
        colors = drawerItemColors(),
        modifier = Modifier.semantics {
            contentDescription = route.label
            if (selected) {
                stateDescription = "Selected"
            }
        }
    )
}

@Composable
private fun drawerItemColors() = NavigationDrawerItemDefaults.colors(
    selectedContainerColor = AuthenticatedShellDefaults.SurfacePressed,
    unselectedContainerColor = AuthenticatedShellDefaults.Surface,
    selectedIconColor = AuthenticatedShellDefaults.Accent,
    unselectedIconColor = AuthenticatedShellDefaults.MutedText,
    selectedTextColor = AuthenticatedShellDefaults.Text,
    unselectedTextColor = AuthenticatedShellDefaults.MutedText
)

private fun RBDartsRoute.drawerIconRes(): Int =
    when (this) {
        RBDartsRoute.Home -> R.drawable.ic_rbdarts_home
        RBDartsRoute.GameType -> R.drawable.ic_rbdarts_game_type
        RBDartsRoute.Players -> R.drawable.ic_rbdarts_players
        RBDartsRoute.Seasons -> R.drawable.ic_rbdarts_seasons
        RBDartsRoute.Handicaps -> R.drawable.ic_rbdarts_handicaps
        RBDartsRoute.Scoring -> R.drawable.ic_rbdarts_scoring
        RBDartsRoute.Settings -> R.drawable.ic_rbdarts_settings
        RBDartsRoute.Loading, RBDartsRoute.Login -> R.drawable.ic_rbdarts_home
    }
