package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CreateEventForm(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = {
            Text("Event name")
        }
    )

}

@PreviewLightDark
@Composable
private fun PreviewCreateEventForm() {
    AttenDTheme {
        CreateEventForm()
    }
}