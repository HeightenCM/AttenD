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
    val boxColor = when(participantStatus){
        ParticipantStatus.PENDING -> Color.Gray
        ParticipantStatus.ACCEPTED -> greenBackground
        ParticipantStatus.REJECTED -> MaterialTheme.colorScheme.onErrorContainer
        ParticipantStatus.CHECKED_IN -> Color.Gray
        ParticipantStatus.CHECKED_OUT -> Color.Gray
    }
    val textColor = when(participantStatus){
        ParticipantStatus.PENDING -> Color.Yellow
        ParticipantStatus.ACCEPTED -> Color.Green
        ParticipantStatus.REJECTED -> Color.Red
        ParticipantStatus.CHECKED_IN -> Color.Blue
        ParticipantStatus.CHECKED_OUT -> Color.Cyan
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
            participantStatus = ParticipantStatus.CHECKED_OUT
        )
    }
}