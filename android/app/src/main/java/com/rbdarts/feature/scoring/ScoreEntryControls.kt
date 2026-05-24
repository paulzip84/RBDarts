package com.rbdarts.feature.scoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbdarts.core.designsystem.RBDartsAuthenticatedPanel
import com.rbdarts.core.designsystem.RBDartsPrimaryAction

@Composable
fun ScoreEntryControls(
    pendingScore: String,
    canSubmit: Boolean,
    onDigit: (Int) -> Unit,
    onClear: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    RBDartsAuthenticatedPanel(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Pending score: ${pendingScore.ifBlank { "--" }}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9)).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { digit ->
                    Button(onClick = { onDigit(digit) }, modifier = Modifier.weight(1f)) {
                        Text(digit.toString())
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onClear, modifier = Modifier.weight(1f)) {
                Text("Clear")
            }
            Button(onClick = { onDigit(0) }, modifier = Modifier.weight(1f)) {
                Text("0")
            }
        }
        RBDartsPrimaryAction("Submit score", onClick = onSubmit, enabled = canSubmit)
    }
}
