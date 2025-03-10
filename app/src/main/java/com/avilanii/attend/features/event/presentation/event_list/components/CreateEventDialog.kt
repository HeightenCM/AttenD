package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun CreateEventDialog(
    eventData: EventUi,
    onDismiss: () -> Unit,
    onSubmit: (String, Int, LocalDate, LocalTime) -> Unit,
    modifier: Modifier = Modifier) {
    var eventName by rememberSaveable { mutableStateOf(eventData.name) }
    var eventBudget by rememberSaveable { mutableIntStateOf(eventData.budget) }
    var eventDate by rememberSaveable { mutableStateOf(eventData.dateTime.value.toLocalDate())}
    var eventTime by rememberSaveable { mutableStateOf(eventData.dateTime.value.toLocalTime()) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Create event icon",
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    text = "Create an event",
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    value = eventName,
                    onValueChange = { newValue ->
                        eventName = newValue
                    },
                    label = {
                        Text("Event name")
                    }
                )
                OutlinedTextField(
                    value = eventBudget.toString(),
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            eventBudget = newValue.toInt()
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text("Event budget")
                    }
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    TimePickerTextField(
                        eventTime = eventTime,
                        onChoseValue = {

                        },
                        modifier = modifier.weight(1f)
                    )
                    DatePickerTextField(
                        eventDate = eventDate,
                        onChoseValue = {

                        },
                        modifier = modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Button(onClick = {
                        onSubmit(eventName, eventBudget, eventDate, eventTime)
                    }) { Text("Create") }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCreateEventDialog() {
    AttenDTheme {
        CreateEventDialog(
            eventData = EventUi(),
            onSubmit = { eventName, eventBudget, eventDate, eventTime ->

            },
            onDismiss = {}
        )
    }
}