package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.features.event.domain.ParticipantStatus
import com.avilanii.attend.ui.theme.AttenDTheme
import com.avilanii.attend.ui.theme.greenBackground

@Composable
fun ParticipantState(
    participantStatus: ParticipantStatus,
    modifier: Modifier = Modifier) {
    val boxColor = if(participantStatus == ParticipantStatus.ACCEPTED){
        greenBackground
    } else if (participantStatus == ParticipantStatus.PENDING){
        Color.Gray
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    val textColor = if(participantStatus == ParticipantStatus.ACCEPTED){
        Color.Green
    } else if (participantStatus == ParticipantStatus.PENDING){
        Color.Yellow
    }
    else {
        MaterialTheme.colorScheme.onErrorContainer
    }
    Box (
        modifier = modifier
            .clip(RoundedCornerShape(30f))
            .background(boxColor)
            .padding()
    ) {
        Text(
            text = " "+participantStatus.name+" ",
            color = textColor)
    }
}

@PreviewLightDark
@Composable
private fun PreviewParticipantStatus() {
    AttenDTheme {
        ParticipantState(
            participantStatus = ParticipantStatus.PENDING
        )
    }
}