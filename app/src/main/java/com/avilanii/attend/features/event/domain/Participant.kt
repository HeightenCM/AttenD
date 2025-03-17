package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.models.Email
import java.time.LocalDateTime

data class Participant(
    val name: String,
    val email: Email,
    val status: ParticipantStatus,
    val joinDate: LocalDateTime? = null
)

enum class ParticipantStatus{
    PENDING, ACCEPTED, REJECTED
}