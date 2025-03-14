package com.avilanii.attend.features.event.data.mappers

import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantDTO
import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.toParticipantEmail
import com.avilanii.attend.features.event.domain.toParticipantStatus

fun ParticipantDTO.toParticipant(): Participant{
    return Participant(
        name = name,
        email = email.toParticipantEmail(),
        status = status!!.toParticipantStatus()
    )
}