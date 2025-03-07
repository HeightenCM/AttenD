package com.avilanii.attend.features.event.presentation.event_create

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.features.event.presentation.event_create.components.CreateEventForm
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CreateEventDialog(modifier: Modifier = Modifier) {
    AlertDialog(
        icon = {
            Icon(Icons.Filled.Edit, "Create event icon")
        },
        title = {
            Text("Create an event")
        },
        text = {
            CreateEventForm()
        },
        onDismissRequest = {

        },
        confirmButton = {

        },
        dismissButton = {

        },

    )
}

@PreviewLightDark
@Composable
private fun PreviewCreateEventDialog() {
    AttenDTheme {
        CreateEventDialog()
    }
}