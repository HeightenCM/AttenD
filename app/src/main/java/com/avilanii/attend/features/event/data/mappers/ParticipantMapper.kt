package com.avilanii.attend.features.event.data.mappers

import com.avilanii.attend.core.domain.models.toEmail
import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantDTO
import com.avilanii.attend.features.event.domain.Participant
import java.time.LocalDateTime

fun ParticipantDTO.toParticipant(): Participant{
    return Participant(
        name = name,
        email = email.toEmail(),
        status = status,
        joinDate = if(joinDate!=null)LocalDateTime.parse(joinDate) else joinDate
    )
}