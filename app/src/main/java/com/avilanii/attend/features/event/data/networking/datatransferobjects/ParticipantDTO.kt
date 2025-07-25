package com.avilanii.attend.features.event.data.networking.datatransferobjects

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.ParticipantStatus
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantDTO(
    val id: Int? = null,
    val eventId: Int,
    val userId: Int? = null,
    val name: String,
    val email: String,
    val status: ParticipantStatus = ParticipantStatus.PENDING,
    val joinDate: String? = "",
    val tier: AttendeeTier? = null
)