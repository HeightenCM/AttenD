package com.avilanii.attend.features.event.presentation.event_participants

import com.avilanii.attend.features.event.presentation.models.ParticipantUi

sealed interface ParticipantListAction {
    data class OnParticipantClick(val participantUi: ParticipantUi): ParticipantListAction
    data object OnAddParticipantClick: ParticipantListAction
    data object OnDismissAddParticipantDialog: ParticipantListAction
    data class OnAddedParticipant(
        val name: String,
        val email: String
    ): ParticipantListAction
    data object OnMenuIconClick: ParticipantListAction
}