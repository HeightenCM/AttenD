package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CreateEventFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    ExtendedFloatingActionButton(
        onClick = {},
        icon = { Icon(Icons.Filled.Add, "Create event icon") },
        text = { Text("Create event") },
        containerColor = MaterialTheme.colorScheme.primary
    )
}

@PreviewLightDark
@Composable
private fun PreviewCreateEventFAB() {
    AttenDTheme {
        CreateEventFAB(onClick = {})
    }
}