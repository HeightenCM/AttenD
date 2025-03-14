package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsResponseDTO(
    val data: List<ParticipantDTO>
)