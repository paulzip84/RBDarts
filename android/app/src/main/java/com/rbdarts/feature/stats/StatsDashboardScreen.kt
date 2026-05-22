package com.rbdarts.feature.stats

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StatsDashboardScreen(projections: List<ProjectionResult>) {
    LazyColumn {
        items(projections) { projection ->
            Row {
                Text(projection.label)
                Text(" ${"%.1f".format(projection.projectedFinalScore)}")
            }
        }
    }
}
