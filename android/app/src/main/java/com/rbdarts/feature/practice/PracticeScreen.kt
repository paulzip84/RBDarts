package com.rbdarts.feature.practice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.rbdarts.core.domain.PracticeAttempt
import com.rbdarts.core.domain.ScoringRules
import java.time.Instant

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PracticeScreen() {
    val target = remember { mutableIntStateOf(5) }
    val scores = remember { mutableStateListOf<Int>() }
    val attempts = scores.mapIndexed { index, score ->
        PracticeAttempt("practice-$index", "local-player", "local-user", target.intValue, score, Instant.now(), Instant.now())
    }
    val stats = ScoringRules.practiceStats(attempts)

    Column {
        Text("Target ${target.intValue}")
        Slider(
            value = target.intValue.toFloat(),
            onValueChange = { target.intValue = it.toInt().coerceAtLeast(1) },
            valueRange = 1f..20f
        )
        FlowRow {
            (0..9).forEach { score ->
                Button(onClick = { scores += score }) { Text("$score") }
            }
        }
        Text("Attempts ${stats.attempts}")
        Text("Average ${"%.1f".format(stats.average)}")
        Text("Best ${stats.bestScore}")
    }
}
