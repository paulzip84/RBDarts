package com.rbdarts.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun RBDartsAdaptiveNavigation(
    currentRoute: RBDartsRoute,
    onDestinationSelected: (RBDartsRoute) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        if (maxWidth >= 840.dp) {
            Row(Modifier.fillMaxSize()) {
                NavigationRail {
                    RBDartsDestinations.topLevel.forEach { destination ->
                        NavigationRailItem(
                            selected = currentRoute == destination,
                            onClick = { onDestinationSelected(destination) },
                            icon = { Text(destination.shortLabel.take(1)) },
                            label = { RBDartsNavigationLabel(destination.shortLabel) }
                        )
                    }
                }
                Box(Modifier.fillMaxSize()) {
                    content(PaddingValues())
                }
            }
        } else {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        RBDartsDestinations.topLevel.forEach { destination ->
                            NavigationBarItem(
                                selected = currentRoute == destination,
                                onClick = { onDestinationSelected(destination) },
                                icon = { Text(destination.shortLabel.take(1)) },
                                label = { RBDartsNavigationLabel(destination.shortLabel) }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(Modifier.padding(paddingValues)) {
                    content(PaddingValues())
                }
            }
        }
    }
}

@Composable
private fun RBDartsNavigationLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        softWrap = false
    )
}
