package com.avilanii.attend.features.event.presentation.event_participants

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import com.avilanii.attend.features.event.presentation.models.toCSV
import com.avilanii.attend.features.event.presentation.models.toParticipantUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParticipantListViewModel(
    private val participantDataSource: ParticipantDataSource,
    private val eventId: Int,
    private val contentResolver: ContentResolver
): ViewModel() {
    private val _state = MutableStateFlow(ParticipantListState())
    val state = _state
        .onStart { loadParticipants() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ParticipantListState()
        )

    private val _events = Channel<ParticipantListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ParticipantListAction){
        when(action) {
            is ParticipantListAction.OnAddParticipantClick -> _state.update {
                it.copy(isAddingParticipant = true)
            }
            is ParticipantListAction.OnAddedParticipant -> addParticipant(
                    name = action.name,
                    email = action.email
                )
            is ParticipantListAction.OnDismissAddParticipantDialog -> _state.update {
                it.copy(isAddingParticipant = false)
            }
            is ParticipantListAction.OnParticipantClick -> {}
            is ParticipantListAction.OnMenuIconClick -> {}
            is ParticipantListAction.OnGenerateInviteQrDismissDialog -> {
                _state.update {
                    it.copy(
                        isShowingInviteQr = false,
                        inviteQr = null
                    )
                }
            }
            is ParticipantListAction.OnGenerateInviteQrOpenDialog -> generateQRInvite()
            is ParticipantListAction.OnScanQrClick -> scanQr(action.qrValue)
            is ParticipantListAction.OnDismissReviewCheckIn -> _state.update {
                it.copy(
                    isReviewingCheckIn = false,
                    checkInResponse = null
                )
            }
            is ParticipantListAction.OnDismissModifyEventTiersClick -> _state.update {
                it.copy(
                    isModifyingAttendeeTiers = false
                )
            }
            is ParticipantListAction.OnModifyEventTiersClick -> loadEventTiers()
            is ParticipantListAction.OnAddEventTierClick -> addEventTier(action.eventTier)
            is ParticipantListAction.OnRemoveEventTierClick -> removeEventTier(action.eventTier)
            is ParticipantListAction.OnExportToCSVClick -> exportToCSV(action.uri)
            is ParticipantListAction.OnImportFromCSVClick -> importFromCSV(action.uri)
        }
    }

    private fun loadParticipants(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            participantDataSource
                .getParticipants(eventId)
                .onSuccess { participants ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            participants = participants.map { it.toParticipantUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun addParticipant(name: String, email: String){
        viewModelScope.launch {
            participantDataSource
                .addParticipant(
                    eventId = eventId,
                    name = name,
                    email = email
                )
                .onSuccess { participantReceived ->
                    _state.update {
                        it.copy(
                            participants = it.participants + participantReceived.toParticipantUi()
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
        _state.update {
            it.copy(isAddingParticipant = false)
        }
    }

    private fun generateQRInvite(){
        viewModelScope.launch {
            participantDataSource
                .generateQRInvite(eventId)
                .onSuccess { receivedQR ->
                    _state.update {
                        it.copy(
                            inviteQr = receivedQR,
                            isShowingInviteQr = true
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun scanQr(qrCode: String){
        viewModelScope.launch {
            participantDataSource
                .scanParticipantQr(eventId, qrCode)
                .onSuccess { checkInResponse ->
                    _state.update {
                        it.copy(
                            checkInResponse = checkInResponse,
                            isReviewingCheckIn = true
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            checkInResponse =
                                CheckInConfirmationDTO(
                                    message = "We couldn't find the QR for this participant!",
                                    accepted = false
                                ),
                            isReviewingCheckIn = true
                        )
                    }
                }
        }
    }

    private fun loadEventTiers(){
        viewModelScope.launch {
            participantDataSource
                .getEventTiers(eventId)
                .onSuccess {  tiers ->
                    _state.update {
                        it.copy(
                            isModifyingAttendeeTiers = true,
                            eventTiers = tiers
                        )
                    }
                }
                .onError {  error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun addEventTier(eventTier: AttendeeTier){
        viewModelScope.launch {
            participantDataSource
                .addEventTier(
                    attendeeTier = eventTier,
                    eventId = eventId
                )
                .onSuccess {
                    _state.update {
                        it.copy(
                            eventTiers = it.eventTiers + eventTier
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isModifyingAttendeeTiers = false
                        )
                    }
                }
        }
    }

    private fun removeEventTier(eventTier: AttendeeTier){
        viewModelScope.launch {
            participantDataSource
                .removeEventTier(
                    attendeeTier = eventTier,
                    eventId = eventId
                )
                .onSuccess {
                    _state.update {
                        it.copy(
                            eventTiers = it.eventTiers - eventTier
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isModifyingAttendeeTiers = false
                        )
                    }
                }
        }
    }

    private fun exportToCSV(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            contentResolver.openOutputStream(uri)?.use {
                it.bufferedWriter().use {
                    it.write(_state.value.participants.toCSV())
                }
            } ?: throw IllegalStateException("Cannot open output stream for $uri")
        }
    }

    private fun importFromCSV(uri: Uri){
        viewModelScope.launch {
            TODO(":(")
        }
    }
}