package com.avilanii.attend.features.event.presentation.attending_events

import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.features.event.presentation.models.QrCode

data class AttendingEventsListState(
    val events: List<EventUi> = emptyList(),
    val isLoading: Boolean = false,
    val isInspectingEvent: Boolean = false,
    val selectedEvent: EventUi? = null,
    val selectedEventQrCode: QrCode? = null
)
