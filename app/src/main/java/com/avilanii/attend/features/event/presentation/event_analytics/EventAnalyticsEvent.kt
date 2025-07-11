package com.avilanii.attend.features.event.presentation.event_analytics

import com.avilanii.attend.core.domain.NetworkError

sealed interface EventAnalyticsEvent {
    data class Error(val error: NetworkError): EventAnalyticsEvent
}