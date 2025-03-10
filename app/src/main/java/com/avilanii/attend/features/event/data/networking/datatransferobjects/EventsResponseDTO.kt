package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class EventsResponseDTO(
    val data: List<EventDTO>
)