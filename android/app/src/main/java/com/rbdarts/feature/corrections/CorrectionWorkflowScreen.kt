package com.rbdarts.feature.corrections

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun CorrectionWorkflowScreen() {
    val reason = remember { mutableStateOf("") }
    val newValue = remember { mutableIntStateOf(0) }

    Column {
        Text("New score: ${newValue.intValue}")
        Slider(
            value = newValue.intValue.toFloat(),
            onValueChange = { newValue.intValue = it.toInt().coerceIn(0, 9) },
            valueRange = 0f..9f,
            steps = 8
        )
        OutlinedTextField(
            value = reason.value,
            onValueChange = { reason.value = it },
            label = { Text("Reason") }
        )
        Button(onClick = {}, enabled = reason.value.isNotBlank()) {
            Text("Submit correction")
        }
    }
}
