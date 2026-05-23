package com.rbdarts.feature.gametype

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader
import com.rbdarts.core.ui.BaseballDartsGameType

@Composable
fun GameTypeScreen(
    onNavigate: (RBDartsRoute) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedType by rememberSaveable { mutableStateOf(BaseballDartsGameType.Standalone.name) }
    val selected = BaseballDartsGameType.valueOf(selectedType)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RBDartsSectionHeader(
            title = "Game type",
            supportingText = "Choose the scoring context before sending players to the board."
        )
        BaseballDartsGameType.entries.forEach { option ->
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(option.label, style = MaterialTheme.typography.titleMedium)
                    Text(option.supportingText, style = MaterialTheme.typography.bodyMedium)
                    RBDartsSecondaryAction(
                        label = if (selected == option) "Selected" else "Choose ${option.label}",
                        onClick = { selectedType = option.name }
                    )
                }
            }
        }
        RBDartsInfoCard(
            title = "Setup summary",
            body = "Selected: ${selected.label}. Players: Avery and Morgan. Season: Spring 2026. Handicap mode: individual player level."
        )
        RBDartsPrimaryAction("Continue to scoring", onClick = { onNavigate(RBDartsRoute.Scoring) })
        RBDartsSecondaryAction("Edit players", onClick = { onNavigate(RBDartsRoute.Players) })
        RBDartsSecondaryAction("Edit handicaps", onClick = { onNavigate(RBDartsRoute.Handicaps) })
    }
}
