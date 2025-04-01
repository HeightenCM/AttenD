package com.avilanii.attend.features.event.presentation.event_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.EventDataSource
import com.avilanii.attend.features.event.presentation.models.toEventUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventListViewModel(
    private val eventDataSource: EventDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(EventListState())
    val state = _state
        .onStart { loadEvents() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventListState()
        )

    private val _events = Channel<EventListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: EventListAction) {
        when (action) {
            is EventListAction.OnCreateEventClick -> _state.update {
                it.copy(
                    isCreatingEvent = true
                )
            }
            is EventListAction.OnCreatedEvent -> {
                createEvent(
                action.eventName,
                action.eventBudget,
                LocalDateTime.of(action.eventDate, action.eventTime))
                _state.update {
                    it.copy(
                        isCreatingEvent = false
                    )
                }
            }
            is EventListAction.OnDismissCreateEventDialog -> _state.update {
                it.copy(
                    isCreatingEvent = false
                )
            }
            is EventListAction.OnEventClick -> {}
            is EventListAction.OnNavigateClick -> {}
        }
    }

    private fun loadEvents(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            eventDataSource
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
                    _events.send(EventListEvent.Error(error))
                }
        }
    }

    private fun createEvent(eventName: String, eventBudget: Int, eventDateTime: LocalDateTime){
        viewModelScope.launch {
            eventDataSource
                .createEvent(
                    eventName = eventName,
                    eventBudget = eventBudget,
                    eventDateTime = eventDateTime.toString()
                )
                .onSuccess { eventReceived ->
                    _state.update {
                        it.copy(
                            events = it.events + eventReceived.toEventUi()
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventListEvent.Error(error))
                }
        }
    }
}