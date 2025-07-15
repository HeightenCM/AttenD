package com.avilanii.attend.features.event.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementDTO(
    val id: Int,
    val title: String,
    val description: String,
    val dateTime: String
)
