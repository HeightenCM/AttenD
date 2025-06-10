package com.avilanii.attend.features.event.presentation.event_participants

import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.presentation.models.ParticipantUi

data class ParticipantListState(
    val isAddingParticipant: Boolean = false,
    val isLoading: Boolean = false,
    val participants: List<ParticipantUi> = emptyList(),
    val selectedParticipant: ParticipantUi? = null,
    val isShowingInviteQr: Boolean = false,
    val inviteQr: String? = null,
    val checkInResponse: CheckInConfirmationDTO? = null,
    val isReviewingCheckIn: Boolean = false,
    val isModifyingAttendeeTiers: Boolean = false,
    val eventTiers: List<AttendeeTier> = emptyList(),
    val isAssigningTier: Boolean = false
)
