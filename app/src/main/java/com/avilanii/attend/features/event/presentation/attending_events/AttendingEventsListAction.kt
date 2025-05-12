package com.avilanii.attend.features.event.presentation.attending_events

import com.avilanii.attend.features.event.domain.ExternalQR
import com.avilanii.attend.features.event.presentation.models.EventUi

sealed interface AttendingEventsListAction {
    data class OnEventClick(val eventUi: EventUi): AttendingEventsListAction
    data object OnDismissEventInspectDialog: AttendingEventsListAction
    data object OnAddEventQrClick: AttendingEventsListAction
    data object OnDismissAddEventQrDialog: AttendingEventsListAction
    data class OnNavigateClick(val index: Int): AttendingEventsListAction
    data class OnAcceptEventInvitationClick(val eventId: Int): AttendingEventsListAction
    data class OnRejectEventInvitationClick(val eventId: Int): AttendingEventsListAction
    data class OnSaveExternalQrClick(val externalQR: ExternalQR): AttendingEventsListAction
}