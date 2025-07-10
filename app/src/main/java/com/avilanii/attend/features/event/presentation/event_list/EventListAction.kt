package com.avilanii.attend.features.event.presentation.event_list

import com.avilanii.attend.features.event.presentation.models.EventUi
import java.time.LocalDateTime

sealed interface EventListAction {
    data class OnEventClick(val eventUi: EventUi): EventListAction
    data object OnCreateEventClick: EventListAction
    data object OnDismissCreateEventDialog: EventListAction
    data class OnCreatedEvent(
        val eventName: String,
        val eventVenue: String,
        val eventStartDate: LocalDateTime,
        val eventEndDate: LocalDateTime
        ): EventListAction
    data class OnNavigateClick(val index: Int): EventListAction
}