package com.avilanii.attend.features.event.presentation.event_iot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.AttenDApp
import com.avilanii.attend.core.data.UserPreferences
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.EventIotDataSource
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
            is EventIotScreenAction.OnAddGateClick -> _state.update {
                it.copy(
                    isAddingSmartGate = true
                )
            }
            is EventIotScreenAction.OnChangeTierStateClick -> changeTierState(action.gateId, action.tierId)
            is EventIotScreenAction.OnCreateGateClick -> addSmartGate(action.name)
            is EventIotScreenAction.OnDismissActivateGateClick -> dismissActivateGate()
            is EventIotScreenAction.OnDismissAddGateClick -> _state.update {
                it.copy(
                    isAddingSmartGate = false
                )
            }
            is EventIotScreenAction.OnDismissGateTierDialog -> _state.update {
                it.copy(
                    isManagingGateTiers = false,
                    tiers = emptyList(),
                    selectedGateId = null
                )
            }
            is EventIotScreenAction.OnGateClick -> {
                if (action.gate.isOnline) {
                    _state.update {
                        it.copy(
                            selectedGateId = action.gate.id
                        )
                    }
                    loadTiers(action.gate.id)
                }
                else
                    activateGate(action.gate.id)
            }
            is EventIotScreenAction.OnMenuIconClick -> {}
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
                .onSuccess { receivedGate ->
                    _state.update {
                        it.copy(
                            smartGates = it.smartGates + receivedGate,
                            isAddingSmartGate = false
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun loadTiers(smartGateId: Int){
        viewModelScope.launch {
            eventIotDataSource
                .loadEventTiers(eventId, smartGateId)
                .onSuccess { tiers ->
                    _state.update {
                        it.copy(
                            isManagingGateTiers = true,
                            tiers = tiers
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun activateGate(smartGateId: Int){
        viewModelScope.launch {
            eventIotDataSource
                .activateSmartGate(eventId, smartGateId)
                .onSuccess { uniqueIdentifier ->
                    AttenDApp.instance.userPrefsStore.updateData {
                        UserPreferences(
                            jwt = it.jwt,
                            gateIdentifier = uniqueIdentifier
                        )
                    }
                    _state.update {
                        it.copy(
                            isActivatingGate = true
                        )
                    }
                }
                .onError { error ->
                    _events.send(EventIotEvent.Error(error))
                }
        }
    }

    private fun dismissActivateGate(){
        viewModelScope.launch {
            AttenDApp.instance.userPrefsStore.updateData {
                UserPreferences(
                    jwt = it.jwt,
                    gateIdentifier = null
                )
            }
            _state.update {
                it.copy(
                    isActivatingGate = false
                )
            }
        }
    }

    private fun changeTierState(gateId: Int, tierId: Int){
        viewModelScope.launch {
            eventIotDataSource
                .changeGateTierState(eventId, gateId, tierId)
                .onSuccess {
                    _state.update {
                        it.copy(
                            tiers = it.tiers.map {  tier ->
                                if (tier.id == tierId)
                                    tier.copy(
                                        isAllowed = !tier.isAllowed!!
                                    )
                                else
                                    tier
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