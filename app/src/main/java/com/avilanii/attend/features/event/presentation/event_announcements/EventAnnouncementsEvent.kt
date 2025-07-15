package com.avilanii.attend.features.event.presentation.event_announcements

import com.avilanii.attend.core.domain.NetworkError

sealed interface EventAnnouncementsEvent {
    data class Error(val error: NetworkError): EventAnnouncementsEvent
}