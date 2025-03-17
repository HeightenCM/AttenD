package com.avilanii.attend.features.event.presentation.event_participants.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.avilanii.attend.core.domain.models.Email
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AddParticipantDialog(
    onDismiss:()-> Unit,
    onSubmit:(String, String)-> Unit,
    modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var isEmailInvalid by rememberSaveable { mutableStateOf(false) }

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
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Add participant icon",
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    text = "Add a participant",
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
                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                    },
                    label = {
                        Text("Email")
                    }
                )
                if(isEmailInvalid){
                    Text("Please enter a valid email!", color = MaterialTheme.colorScheme.error)
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Button(onClick = {
                        try {
                            Email(email)
                            onSubmit(name, email)
                        } catch (e: IllegalArgumentException){
                            isEmailInvalid = true
                            e.printStackTrace()
                        }
                    }) { Text("Create") }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddParticipantDialog() {
    AttenDTheme { 
        AddParticipantDialog(
            onDismiss = {},
            onSubmit = { name, email ->
                Log.wtf("Email", "Success!")
            },
            modifier = Modifier
        )
    }
}