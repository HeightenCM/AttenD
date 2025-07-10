package com.avilanii.attend.features.event.data.mappers

import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventDTO
import com.avilanii.attend.features.event.domain.Event
import java.time.LocalDateTime

fun EventDTO.toEvent(): Event{
    return Event(
        id = id!!,
        name = name,
        venue = venue,
        startDateTime = LocalDateTime.parse(startDateTime),
        endDateTime = LocalDateTime.parse(endDateTime),
        isPending = isPending,
        organizerName = organizer
    )
}