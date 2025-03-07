package com.avilanii.attend.features.event.presentation.event_create.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDebug(
    modifier: Modifier = Modifier,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog (
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ){
        DatePicker(datePickerState)
    }
}

@PreviewLightDark
@Composable
private fun PreviewDatePickerDebug() {
    AttenDTheme {
        DatePickerDebug(onDateSelected = {}, onDismiss = {})
    }
}