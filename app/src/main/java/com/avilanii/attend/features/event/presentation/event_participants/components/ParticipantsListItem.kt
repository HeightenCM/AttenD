package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avilanii.attend.features.event.domain.Email
import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantStatus
import com.avilanii.attend.features.event.presentation.models.ParticipantUi
import com.avilanii.attend.features.event.presentation.models.toParticipantUi
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun ParticipantListItem(
    participantUi: ParticipantUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (modifier = modifier
        .clickable { onClick() }
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (horizontalAlignment = Alignment.Start) {
            Text(participantUi.name, fontSize = 30.sp)
            Text(participantUi.email, fontSize = 15.sp)
        }
        ParticipantState(
            participantStatus = participantUi.status
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewParticipantsListItem() {
    AttenDTheme {
        ParticipantListItem(
            participantUi = previewParticipant,
            onClick = {},
            modifier = Modifier
        )
    }
}

internal val previewParticipant = Participant(
    name = "Joshy",
    email = Email("mrjosh@gmail.com"),
    status = ParticipantStatus.ACCEPTED
).toParticipantUi()