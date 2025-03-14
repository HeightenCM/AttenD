package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantDTO(
    val name: String,
    val email: String,
    val status: Int? = null
)