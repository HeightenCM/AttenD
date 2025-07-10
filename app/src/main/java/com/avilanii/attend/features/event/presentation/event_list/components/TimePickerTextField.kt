package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    label: String,
    eventTime: LocalTime,
    onChoseValue: (LocalTime) -> Unit,
    modifier: Modifier = Modifier) {
    val fieldColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    var isDialogOpen by remember { mutableStateOf(false) }
    var timePickerState by remember { mutableStateOf(TimePickerState(eventTime.hour, eventTime.minute, false)) }
    var selectedTime by remember { mutableStateOf(eventTime) }

    if (isDialogOpen) {
        TimePickerDialog (
            onConfirm = {
                isDialogOpen = false
                selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                onChoseValue(selectedTime)
            },
            onDismiss = {
                isDialogOpen = false
            }
        ) {
            TimePicker(
                state = timePickerState
            )
        }
    }

    OutlinedTextField(
        value = selectedTime.truncatedTo(ChronoUnit.MINUTES).toString(),
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = fieldColor,
            disabledBorderColor = fieldColor,
            disabledLabelColor = fieldColor),
        modifier = modifier
            .fillMaxWidth()
            .clickable { isDialogOpen = true }
    )
}

@PreviewLightDark
@Composable
private fun PreviewTimePickerTextField() {
    AttenDTheme {
        TimePickerTextField(
            label = "Time",
            eventTime = LocalTime.now(),
            onChoseValue = {},
        )
    }
}