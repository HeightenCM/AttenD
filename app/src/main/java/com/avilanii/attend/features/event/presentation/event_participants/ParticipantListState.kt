package com.avilanii.attend.features.event.presentation.event_participants

import com.avilanii.attend.features.event.presentation.models.ParticipantUi

data class ParticipantListState(
    val isAddingParticipant: Boolean = false,
    val isLoading: Boolean = false,
    val participants: List<ParticipantUi> = emptyList(),
    val isShowingInviteQr: Boolean = false,
    val inviteQr: String? = null
)
