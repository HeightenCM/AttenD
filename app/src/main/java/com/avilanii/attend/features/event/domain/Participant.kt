package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.models.Email
import kotlinx.serialization.Serializable

data class Participant(
    val id: Int,
    val name: String,
    val email: Email,
    val status: ParticipantStatus,
    val tier: AttendeeTier? = null
)

@Serializable
enum class ParticipantStatus{
    PENDING, ACCEPTED, REJECTED, CHECKED_IN, CHECKED_OUT
}