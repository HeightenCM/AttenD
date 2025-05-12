package com.avilanii.attend.features.event.presentation.event_participants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import com.avilanii.attend.features.event.presentation.models.toParticipantUi
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
    private val eventId: Int
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
}