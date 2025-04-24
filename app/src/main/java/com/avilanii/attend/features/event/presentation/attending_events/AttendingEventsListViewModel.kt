package com.avilanii.attend.features.event.presentation.attending_events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.AttendingEventDataSource
import com.avilanii.attend.features.event.presentation.models.toEventUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AttendingEventsListViewModel(
    private val attendingEventDataSource: AttendingEventDataSource
) : ViewModel(){
    private val _state = MutableStateFlow(AttendingEventsListState())
    val state = _state
        .onStart { loadEvents() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AttendingEventsListState()
        )

    private val _events = Channel<AttendingEventListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AttendingEventsListAction) {
        when (action) {
            is AttendingEventsListAction.OnAddEventQrClick -> {
                TODO("Camera functionality for scanning QR")
            }
            is AttendingEventsListAction.OnDismissEventInspectDialog -> _state.update {
                it.copy(
                    isInspectingEvent = false
                )
            }
            is AttendingEventsListAction.OnDismissAddEventQrDialog -> {
                TODO("Add AddEventDialog")
            }
            is AttendingEventsListAction.OnEventClick -> _state.update {
                it.copy(
                    isInspectingEvent = true
                )
            }
            is AttendingEventsListAction.OnNavigateClick -> {}
            is AttendingEventsListAction.OnAcceptEventInvitationClick -> {
                respondToEvent(action.eventId, true)
            }
            is AttendingEventsListAction.OnRejectEventInvitationClick -> {
                respondToEvent(action.eventId, false)
            }
        }
    }

    private fun loadEvents(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            attendingEventDataSource
                .getEvents()
                .onSuccess { events ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            events = events.map { it.toEventUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(AttendingEventListEvent.Error(error))
                }
        }
    }

    private fun addEvent(qrValue: Int){
        viewModelScope.launch {
            attendingEventDataSource
                .addEvent(
                    qrValue = qrValue
                )
                .onSuccess { eventReceived ->
                    _state.update {
                        it.copy(
                            events = it.events + eventReceived.toEventUi()
                        )
                    }
                }
                .onError { error ->
                    _events.send(AttendingEventListEvent.Error(error))
                }
        }
    }

    private fun respondToEvent(eventId: Int, isAccepted: Boolean){
        viewModelScope.launch {
            attendingEventDataSource
                .respondEvent(
                    eventId = eventId,
                    isAccepted = isAccepted
                )
                .onSuccess {
                    loadEvents()
                }
                .onError { error ->
                    _events.send(AttendingEventListEvent.Error(error))
                }
        }
    }
}