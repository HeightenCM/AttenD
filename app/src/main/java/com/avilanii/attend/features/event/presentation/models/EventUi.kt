package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.Event
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class EventUi(
    val id: Int = 0,
    val name: String = "",
    val dateTime: DisplayableDateTime = LocalDateTime.now().toDisplayableDateTime(),
    val budget: Int? = null,
    val organizer: String? = null,
    val venue: String? = null
)

data class DisplayableDateTime(
    val value: LocalDateTime,
    val formatted: String
)

fun Event.toEventUi():EventUi{
    return EventUi(
        id = this.id,
        name = this.name,
        dateTime = this.dateTime.toDisplayableDateTime(),
        budget = this.budget)
}

fun LocalDateTime.toDisplayableDateTime(): DisplayableDateTime{
    val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, HH:mm")
    return DisplayableDateTime(
        value = this,
        formatted = this.format(dateTimeFormatter)
    )
}

fun LocalDateTime.toDisplayableDate(): DisplayableDateTime{
    val dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    return DisplayableDateTime(
        value = this,
        formatted = this.format(dateTimeFormatter)
    )
}

fun LocalDateTime.toDisplayableTime(): DisplayableDateTime{
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return DisplayableDateTime(
        value = this,
        formatted = this.format(dateTimeFormatter)
    )
}