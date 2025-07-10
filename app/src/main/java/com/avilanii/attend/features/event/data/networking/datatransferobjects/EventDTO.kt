package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class EventDTO (
    val id: Int? = null,
    val name: String,
    val venue: String,
    val startDateTime: String,
    val endDateTime: String,
    val isPending: Boolean? = null,
    val organizer: String? = null

)