package com.avilanii.attend.features.event.presentation.event_create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CreateEventForm(modifier: Modifier = Modifier) {
    val eventName = ""
    var eventBudget = ""
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = eventName,
            onValueChange = {},
            label = {
                Text("Event name")
            }
        )
        OutlinedTextField(
            value = eventBudget,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    eventBudget = newValue
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCreateEventForm() {
    AttenDTheme {
        CreateEventForm()
    }
}