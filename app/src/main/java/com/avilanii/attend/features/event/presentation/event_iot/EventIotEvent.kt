package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.core.domain.NetworkError

sealed interface EventIotEvent {
    data class Error(val error: NetworkError): EventIotEvent
}