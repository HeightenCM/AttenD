package com.avilanii.attend.features.event.presentation.event_list

import com.avilanii.attend.core.domain.NetworkError

sealed interface EventListEvent {
    data class Error(val error: NetworkError): EventListEvent
}