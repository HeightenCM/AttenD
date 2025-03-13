package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class EventDTO (
    val id: Int? = null,
    val name: String,
    val budget: Int,
    val dateTime: String

)