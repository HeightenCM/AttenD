package com.avilanii.attend.features.event.presentation.event_participants

import android.net.Uri
import com.avilanii.attend.features.event.domain.AttendeeTier
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
    data object OnGenerateInviteQrOpenDialog: ParticipantListAction
    data object OnGenerateInviteQrDismissDialog: ParticipantListAction
    data class OnScanQrClick(val qrValue: String): ParticipantListAction
    data object OnDismissReviewCheckIn: ParticipantListAction
    data object OnModifyEventTiersClick: ParticipantListAction
    data object OnDismissModifyEventTiersClick: ParticipantListAction
    data class OnRemoveEventTierClick(val eventTier: AttendeeTier): ParticipantListAction
    data class OnAddEventTierClick(val eventTier: AttendeeTier): ParticipantListAction
    data class OnAssignParticipantTierClick(val participant: ParticipantUi, val attendeeTier: AttendeeTier): ParticipantListAction
    data class OnResignParticipantTierClick(val participant: ParticipantUi): ParticipantListAction
    data class OnExportToCSVClick(val uri: Uri): ParticipantListAction
    data class OnImportFromCSVClick(val uri: Uri): ParticipantListAction
}