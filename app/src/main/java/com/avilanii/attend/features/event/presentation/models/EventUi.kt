package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.Event
import java.time.format.DateTimeFormatter

data class EventUi(
    val id: Int,
    val name: String,
    val dateTime: String,
    val budget: Int
)

fun Event.toEventUi():EventUi{
    val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, HH:mm")
    return EventUi(
        id = this.id,
        name = this.name,
        dateTime = this.dateTime.format(dateTimeFormatter),
        budget = this.budget)
}