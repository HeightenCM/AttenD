package com.avilanii.attend.features.event.presentation.event_list

import com.avilanii.attend.features.event.presentation.models.EventUi
import java.time.LocalDate
import java.time.LocalTime

sealed interface EventListAction {
    data class OnEventClick(val eventUi: EventUi): EventListAction
    data object OnCreateEventClick: EventListAction
    data object OnDismissCreateEventDialog: EventListAction
    data class OnCreatedEvent(
        val eventName: String,
        val eventBudget: Int,
        val eventDate: LocalDate,
        val eventTime: LocalTime
        ): EventListAction
    data class OnNavigateClick(val index: Int): EventListAction
}