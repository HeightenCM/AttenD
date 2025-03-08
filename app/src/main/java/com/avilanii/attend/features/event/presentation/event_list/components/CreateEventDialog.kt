package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CreateEventDialog(
    onDismiss: () -> Unit,
    onSubmit: (EventUi) -> Unit,
    modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        var event = remember { mutableStateOf(EventUi()) }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            OutlinedTextField(
                value = event.value.name,
                onValueChange = { newValue ->
                    event.value.copy(
                        name = newValue
                    )
                },
                label = {
                    Text("Event name")
                }
            )
            OutlinedTextField(
                value = event.value.budget.toString(),
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        event.value.copy(
                            budget = newValue.toInt()
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text("Event budget")
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(onClick ={
                    onSubmit(event.value)
                }) { Text("Create") }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCreateEventDialog() {
    AttenDTheme {
        CreateEventDialog(
            onSubmit = {},
            onDismiss = {}
        )
    }
}