package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    onChoseValue: (Long?, Long?) -> Unit,
    modifier: Modifier = Modifier) {
    val fieldColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
        initialSelectedEndDateMillis = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli(),
    )

    if(isDialogOpen) {
        DatePickerModal(
            onConfirm = {
                isDialogOpen = false
                onChoseValue(dateRangePickerState.selectedStartDateMillis, dateRangePickerState.selectedEndDateMillis)
            },
            onDismiss = { isDialogOpen = false }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                title = {
                    Text(
                        text = "Select date range"
                    )
                }
            )
        }
    }
    OutlinedTextField(
        value = "From ${dateRangePickerState.selectedStartDateMillis?.let { millis ->
            Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
        } ?: "ERROR"}" +
                " to ${dateRangePickerState.selectedEndDateMillis?.let { millis ->
                    Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
                } ?: "ERROR"}",
        onValueChange = {},
        label = { Text("Date interval") },
        readOnly = true,
        enabled = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = fieldColor,
            disabledBorderColor = fieldColor,
            disabledLabelColor = fieldColor),
        modifier = modifier
            .alpha(1f)
            .fillMaxWidth()
            .clickable { isDialogOpen = true }
    )
}

@PreviewLightDark
@Composable
private fun PreviewDatePickerTextField() {
    AttenDTheme {
        DatePickerTextField(
            onChoseValue = { start, end ->
            }
        )
    }
}