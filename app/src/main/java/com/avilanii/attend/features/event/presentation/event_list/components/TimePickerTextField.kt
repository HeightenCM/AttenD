package com.avilanii.attend.features.event.presentation.event_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerTextField(
    onChoseValue: (LocalTime) -> Unit,
    modifier: Modifier = Modifier) {
    val time = LocalTime.now()
    var isDialogOpen = remember { mutableStateOf(false) }
    var timePickerState = remember { mutableStateOf(TimePickerState(time.hour, time.minute, false)) }
    var selectedTime = remember { mutableStateOf(time) }

    if (isDialogOpen.value) {
        TimePickerDialog(
            onConfirm = {
                isDialogOpen.value = false
                selectedTime.value = LocalTime.of(timePickerState.value.hour, timePickerState.value.minute)
                onChoseValue(selectedTime.value)
            },
            onDismiss = {
                isDialogOpen.value = false
            }
        ) {
            TimePicker(
                state = timePickerState.value
            )
        }
    }

    OutlinedTextField(
        value = selectedTime.value.hour.toString()+":"+selectedTime.value.minute.toString(),
        onValueChange = { newValue ->
            onChoseValue(LocalTime.parse(newValue.toString(), DateTimeFormatter.ofPattern("HH:mm")))
        },
        label = { Text("Event time") },
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .alpha(1f)
            .fillMaxWidth()
            .clickable { isDialogOpen.value = true }
    )
}

@PreviewLightDark
@Composable
private fun PreviewTimePickerTextField() {
    AttenDTheme {
        TimePickerTextField(
            onChoseValue = {},
        )
    }
}