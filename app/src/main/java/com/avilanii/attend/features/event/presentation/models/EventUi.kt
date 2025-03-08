package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class EventUi(
    val id: Int = 0,
    val name: String = "",
    val dateTime: DisplayableDate = LocalDateTime.now().toDisplayableDate(),
    val budget: Int = 0
)

data class DisplayableDate(
    val value: LocalDateTime,
    val formatted: String
)

fun Event.toEventUi():EventUi{
    return EventUi(
        id = this.id,
        name = this.name,
        dateTime = this.dateTime.toDisplayableDate(),
        budget = this.budget)
}

fun LocalDateTime.toDisplayableDate(): DisplayableDate{
    val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, HH:mm")
    return DisplayableDate(
        value = this,
        formatted = this.format(dateTimeFormatter)
    )
}