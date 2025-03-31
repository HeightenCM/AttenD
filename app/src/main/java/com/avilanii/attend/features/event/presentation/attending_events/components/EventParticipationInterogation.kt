package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun EventParticipationInterogation(
    onAccept:()-> Unit,
    onReject:()-> Unit,
    modifier: Modifier = Modifier,
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You've been invited to join this event. Will you?",
            color = MaterialTheme.colorScheme.secondary)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onReject
            ) {
                Text("No")
            }
            Button(
                onClick = onAccept
            ) {
                Text("Yes")
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun PreviewEventParticipationInterogation() {
    AttenDTheme {
        EventParticipationInterogation(
            onAccept = {  },
            onReject = {  }
        )
    }
}