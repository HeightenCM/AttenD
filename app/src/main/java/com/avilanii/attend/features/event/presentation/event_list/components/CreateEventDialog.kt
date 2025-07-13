package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun CreateEventDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, LocalDateTime, LocalDateTime) -> Unit,
    modifier: Modifier = Modifier) {
    var eventName by rememberSaveable { mutableStateOf("") }
    var eventVenue by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable {
        mutableStateOf(LocalDateTime.now())
    }
    var endDate by rememberSaveable {
        mutableStateOf(LocalDateTime.now().plusDays(1).plusHours(1))
    }

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
                        Text("Name")
                    }
                )
                OutlinedTextField(
                    value = eventVenue,
                    onValueChange = { newValue ->
                        eventVenue = newValue
                    },
                    label = {
                        Text("Venue")
                    }
                )
                Column {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimePickerTextField(
                            label = "Start time",
                            eventTime = startDate.toLocalTime(),
                            onChoseValue = { newTime ->
                                startDate = startDate.with(newTime)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TimePickerTextField(
                            label = "End time",
                            eventTime = endDate.toLocalTime(),
                            onChoseValue = { newTime ->
                                endDate = endDate.with(newTime)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    DatePickerTextField(
                        onChoseValue = { startDateReceived, endDateReceived ->
                            startDateReceived?.let { millis ->
                                val newDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                                startDate = startDate.withYear(newDate.year)
                                    .withMonth(newDate.monthValue)
                                    .withDayOfMonth(newDate.dayOfMonth)
                            }

                            endDateReceived?.let { millis ->
                                val newDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()

                                endDate = endDate.withYear(newDate.year)
                                    .withMonth(newDate.monthValue)
                                    .withDayOfMonth(newDate.dayOfMonth)
                            }
                        },
                        givenStartDate = startDate.toLocalDate(),
                        givenEndDate = endDate.toLocalDate()
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Button(onClick = {
                        onSubmit(eventName, eventVenue, startDate, endDate)
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
            onSubmit = { eventName, eventBudget, startDate, endDate ->

            },
            onDismiss = {}
        )
    }
}