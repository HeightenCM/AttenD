package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    givenStartDate: LocalDate,
    givenEndDate: LocalDate,
    onChoseValue: (Long?, Long?) -> Unit,
    modifier: Modifier = Modifier) {
    val fieldColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }
    var startDate by rememberSaveable {
        mutableLongStateOf(givenStartDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
    }
    var endDate by rememberSaveable {
        mutableLongStateOf(givenEndDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
    }

    if(isDialogOpen) {
        DateRangePickerModal(
            startDate = startDate,
            endDate = endDate,
            onDateRangeSelected = {
                startDate = it.first ?: startDate
                endDate = it.second ?: endDate
                onChoseValue(it.first, it.second)
                isDialogOpen = false
            }
        ) {
            isDialogOpen = false
        }
    }
    OutlinedTextField(
        value = "From ${
            startDate.let { millis ->
                Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("d MMMM yyyy"))
            } ?: "ERROR"}" +
                " to ${
                    endDate.let { millis ->
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
            },
            givenStartDate = LocalDate.now(),
            givenEndDate = LocalDate.now().plusDays(1),
        )
    }
}