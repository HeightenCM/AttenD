package com.avilanii.attend.features.event.domain

import java.time.LocalDateTime

data class Event(
    val id: Int,
    val name: String,
    val dateTime: LocalDateTime,
    val budget: Int,
    val venue: String? = null,
    val organizerName: String? = null,
    val isPending: Boolean? = null
)
