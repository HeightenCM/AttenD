package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.SmartGate

sealed interface EventIotScreenAction {
    data object OnAddIotClick: EventIotScreenAction
    data class OnDismissAddIotClick(val isAdding: Boolean): EventIotScreenAction
    data object OnMenuIconClick: EventIotScreenAction
    data class OnAddingGateClick(val name: String): EventIotScreenAction
    data class OnRemoveGateTierClick(val smartGate: SmartGate, val tier: Pair<AttendeeTier, Int>): EventIotScreenAction
    data object OnAddGateTierClick: EventIotScreenAction
}