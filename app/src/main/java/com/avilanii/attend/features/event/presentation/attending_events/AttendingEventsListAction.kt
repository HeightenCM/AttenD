package com.avilanii.attend.features.event.presentation.attending_events

sealed interface AttendingEventsListAction {
    data class OnEventClick(val eventId: Int): AttendingEventsListAction
    data object OnAddEventQrClick: AttendingEventsListAction
    data object OnDismissAddEventQrDialog: AttendingEventsListAction
}