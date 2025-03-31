package com.avilanii.attend.features.event.presentation.attending_events

import com.avilanii.attend.core.domain.NetworkError

sealed interface AttendingEventListEvent {
    data class Error(val error: NetworkError): AttendingEventListEvent
}