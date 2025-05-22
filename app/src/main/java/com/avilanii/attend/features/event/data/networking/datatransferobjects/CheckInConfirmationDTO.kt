package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class CheckInConfirmationDTO(
    val message: String,
    val accepted: Boolean
)