package com.avilanii.attend.features.event.presentation.event_participants

import com.avilanii.attend.core.domain.NetworkError

sealed interface ParticipantListEvent {
    data class Error(val error: NetworkError): ParticipantListEvent
}