package com.avilanii.attend.features.event.presentation.event_list

import com.avilanii.attend.features.event.presentation.models.EventUi

data class EventListState(
    val selectedEvent:EventUi? = null,
    val isLoading: Boolean = false,
    val events: List<EventUi> = emptyList()
)
