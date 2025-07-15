package com.avilanii.attend.features.event.domain

import java.time.LocalDateTime

data class Announcement(
    val id: Int,
    val title: String,
    val description: String,
    val dateTime: LocalDateTime
)
