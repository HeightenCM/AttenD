package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.SmartGate

sealed interface EventIotScreenAction {
    data object OnAddGateClick: EventIotScreenAction
    data object OnDismissAddGateClick: EventIotScreenAction
    data class OnCreateGateClick(val name: String): EventIotScreenAction
    data class OnGateClick(val gate: SmartGate): EventIotScreenAction
    data object OnDismissActivateGateClick: EventIotScreenAction
    data object OnDismissGateTierDialog: EventIotScreenAction
    data class OnChangeTierStateClick(val gateId: Int, val tierId: Int): EventIotScreenAction
    data object OnMenuIconClick: EventIotScreenAction
}