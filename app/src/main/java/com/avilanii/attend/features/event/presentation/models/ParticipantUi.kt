package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantStatus

data class ParticipantUi(
    val name: String,
    val email: String,
    val status: ParticipantStatus
)

fun Participant.toParticipantUi(): ParticipantUi{
    return ParticipantUi(
        name = name,
        email = email.value,
        status = status
    )
}
