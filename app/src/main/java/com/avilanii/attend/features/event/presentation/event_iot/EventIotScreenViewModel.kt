package com.avilanii.attend.features.event.presentation.event_iot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.AttenDApp
import com.avilanii.attend.core.data.UserPreferences
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
            is EventIotScreenAction.OnAddGateTierClick -> loadTiers(action.smartGate)
            is EventIotScreenAction.OnAddIotClick -> _state.update {
                it.copy(
                    isAddingSmartGate = true
                )
            }
            is EventIotScreenAction.OnAddingGateClick -> addSmartGate(action.name)
            is EventIotScreenAction.OnDismissAddIotClick -> _state.update {
                it.copy(
                    isSmartGateAdded = false,
                    isAddingSmartGate = false
                )
            }
            is EventIotScreenAction.OnMenuIconClick -> {}
            is EventIotScreenAction.OnRemoveGateTierClick -> removeGateTier(
                smartGate = action.smartGate,
                attendeeTier = action.tier.first
            )
            is EventIotScreenAction.OnChoseToAddGateTier -> addGateTier(action.gate, action.tier)
            is EventIotScreenAction.OnDismissAddGateTierClick ->{
                _state.update {
                    it.copy(
                        isAddingGateTier = false,
                        selectedGate = null,
                        tiers = emptyList()
                    )
                }
                viewModelScope.launch {
                    AttenDApp.instance.userPrefsStore.updateData {
                        UserPreferences(
                            jwt = it.jwt,
                            gateIdentifier = null
                        )
                    }
                }
            }
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
                .onSuccess { receivedUniqueRandID ->
                    _state.update {
                        it.copy(
                            smartGates = it.smartGates + SmartGate(name),
                            isSmartGateAdded = true
                        )
                    }
                    AttenDApp.instance.userPrefsStore.updateData {
                        UserPreferences(
                            jwt = it.jwt,
                            gateIdentifier = receivedUniqueRandID
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun loadTiers(smartGate: SmartGate){
        viewModelScope.launch {
            eventIotDataSource
                .loadEventTiers(eventId)
                .onSuccess { tiers ->
                    _state.update {
                        it.copy(
                            isAddingGateTier = true,
                            selectedGate = smartGate,
                            tiers = tiers
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isAddingGateTier = false
                        )
                    }
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
                            },
                            isAddingGateTier = false
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isAddingGateTier = false
                        )
                    }
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