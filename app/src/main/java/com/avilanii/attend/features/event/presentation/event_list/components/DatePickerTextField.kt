package com.avilanii.attend.features.event.presentation.event_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    eventDate: LocalDate,
    onChoseValue: (LocalDate) -> Unit,
    modifier: Modifier = Modifier) {
    val fieldColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    var isDialogOpen by remember { mutableStateOf(false) }
    var datePickerState by remember { mutableStateOf(DatePickerState(
        initialSelectedDateMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        locale = Locale.getDefault()
    )) }
    var selectedDate by remember { mutableStateOf(eventDate) }

    if(isDialogOpen) {
        DatePickerModal(
            onConfirm = {
                isDialogOpen = false
                selectedDate = datePickerState.selectedDateMillis.let{Instant.ofEpochMilli(it!!).atZone(ZoneId.systemDefault()).toLocalDate()}
                onChoseValue(selectedDate)
            },
            onDismiss = { isDialogOpen = false }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
    OutlinedTextField(
        value = selectedDate.toString(),
        onValueChange = {},
        label = { Text("Event date") },
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
            eventDate = LocalDate.now(),
            onChoseValue = { value ->
                Log.wtf("date", value.toString())
            }
        )
    }
}