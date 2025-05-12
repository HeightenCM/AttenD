package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AddExternalEventDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var isTitleInvalid by rememberSaveable { mutableStateOf(false) }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                modifier = modifier.padding(10.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("We couldn't find the event its QR you just scanned! " +
                        "It might be an external event. If so, please provide a title in order to save it. " +
                        "Otherwise, dismiss this dialog.",
                    textAlign = TextAlign.Center)
                OutlinedTextField(
                    value = title,
                    onValueChange = {newTitle ->
                        title = newTitle
                    },
                    label = {
                        Text("Title")
                    }
                )
                if (isTitleInvalid){
                    Text("Don't forget to assign a title!", color = MaterialTheme.colorScheme.error)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) { Text("Dismiss", color = MaterialTheme.colorScheme.error) }
                    Button(
                        onClick = {
                            if (title.isNotEmpty())
                                onSave(title)
                            else
                                isTitleInvalid = true
                        }
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddExternalEventDialog() {
    AttenDTheme {
        AddExternalEventDialog(
            onDismiss = {},
            onSave = {}
        )
    }
}