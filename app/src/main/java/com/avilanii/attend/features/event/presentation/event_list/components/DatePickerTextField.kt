package com.avilanii.attend.features.event.presentation.event_list.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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
    var selectedDate by remember { mutableStateOf<Long?>(eventDate.atStartOfDay().toInstant(
        ZoneOffset.UTC).toEpochMilli()) }
    if(isDialogOpen) {
        DatePickerModal(
            selectedDate = selectedDate,
            onDateSelected = {
                selectedDate = it
                isDialogOpen = false
            },
            onDismiss = { isDialogOpen = false }
        )
    }
    OutlinedTextField(
        value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(selectedDate),
        onValueChange = { newValue ->
            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            onChoseValue(LocalDate.parse(newValue, formatter))
        },
        label = { Text("Event time") },
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