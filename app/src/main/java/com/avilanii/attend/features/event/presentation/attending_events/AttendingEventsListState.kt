package com.avilanii.attend.features.event.presentation.attending_events

import com.avilanii.attend.features.event.domain.Announcement
import com.avilanii.attend.features.event.domain.ExternalQR
import com.avilanii.attend.features.event.presentation.models.EventUi

data class AttendingEventsListState(
    val events: List<EventUi> = emptyList(),
    val externalEvents: List<ExternalQR> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = false,
    val isInspectingEvent: Boolean = false,
    val isEventNotFound: Boolean = false,
    val isViewingAnnouncements: Boolean = false,
    val selectedEventTitle: String? = null,
    val selectedQr: String? = null,
    val scannedQr: String? = null,
)
