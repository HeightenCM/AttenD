package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SensorDoor
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AddSmartGateDialog(
    modifier: Modifier = Modifier,
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card (
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.SensorDoor,
                    contentDescription = "Add SmartGate",
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    text = "Add a smart gate",
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        name = newValue
                    },
                    label = {
                        Text("Name")
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text("Cancel")
                    }
                    Button(onClick = {
                        onSubmit(name)
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddSmartGateDialog() {
    AttenDTheme {
        AddSmartGateDialog(
            onSubmit = {}
        ){

        }
    }
}