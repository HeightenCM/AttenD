package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.models.Email

data class Participant(
    val name: String,
    val email: Email,
    val status: ParticipantStatus
)

enum class ParticipantStatus{
    PENDING, ACCEPTED, REJECTED
}

fun Int.toParticipantStatus(): ParticipantStatus{
    return when(this){
        0 -> ParticipantStatus.ACCEPTED
        1 -> ParticipantStatus.PENDING
        else -> ParticipantStatus.REJECTED
    }
}