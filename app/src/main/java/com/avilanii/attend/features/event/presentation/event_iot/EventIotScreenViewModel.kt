package com.avilanii.attend.features.event.presentation.event_iot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.EventIotDataSource
import com.avilanii.attend.features.event.domain.SmartGate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventIotScreenViewModel(
    private val eventIotDataSource: EventIotDataSource,
    private val eventId: Int
): ViewModel() {
    private val _state = MutableStateFlow(EventIotScreenState())
    val state = _state
        .onStart {
            loadIotDevices()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventIotScreenState()
        )

    private val _events = Channel<EventIotEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: EventIotScreenAction){
        when(action){
            is EventIotScreenAction.OnAddGateTierClick -> _state.update {
                it.copy(
                    isAddingGateTier = true
                )
            }
            is EventIotScreenAction.OnAddIotClick -> _state.update {
                it.copy(
                    isAddingSmartGate = true
                )
            }
            is EventIotScreenAction.OnAddingGateClick -> addSmartGate(action.name)
            is EventIotScreenAction.OnDismissAddIotClick -> TODO()
            is EventIotScreenAction.OnMenuIconClick -> {}
            is EventIotScreenAction.OnRemoveGateTierClick -> removeGateTier(
                smartGate = action.smartGate,
                attendeeTier = action.tier.first
            )
        }
    }

    private fun loadIotDevices(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            eventIotDataSource
                .getIotDevices(eventId)
                .onSuccess { retrievedGates ->
                    _state.update {
                        it.copy(
                            smartGates = retrievedGates,
                            isLoading = false
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun addSmartGate(name: String){
        viewModelScope.launch {
            eventIotDataSource
                .addSmartGate(eventId, name)
                .onSuccess {
                    _state.update {
                        it.copy(
                            smartGates = it.smartGates + SmartGate(name)
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun addGateTier(smartGate: SmartGate, attendeeTier: AttendeeTier){
        viewModelScope.launch {
            eventIotDataSource
                .addGateTier(eventId, smartGate.name, attendeeTier)
                .onSuccess {
                    _state.update {
                        it.copy(
                            smartGates = it.smartGates.map { gate ->
                                if (gate.name == smartGate.name) {
                                    gate.copy(
                                        allowedTiers = gate.allowedTiers + Pair(attendeeTier, 0)
                                    )
                                } else {
                                    gate
                                }
                            }
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun removeGateTier(smartGate: SmartGate, attendeeTier: AttendeeTier){
        viewModelScope.launch {
            eventIotDataSource
                .removeGateTier(eventId, smartGate.name, attendeeTier)
                .onSuccess {
                    _state.update {
                        it.copy(
                            smartGates = it.smartGates.map {  gate ->
                                if (gate.name == smartGate.name) {
                                    gate.copy(
                                        allowedTiers = gate.allowedTiers.filterNot { it.first.title == attendeeTier.title }
                                    )
                                } else {
                                    gate
                                }
                            }
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }
}