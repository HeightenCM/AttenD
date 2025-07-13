package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.SmartGate

data class EventIotScreenState(
    val smartGates: List<SmartGate> = emptyList(),
    val tiers: List<AttendeeTier> = emptyList(),
    val isAddingSmartGate: Boolean = false,
    val isActivatingGate: Boolean = false,
    val isManagingGateTiers: Boolean = false,
    val isLoading: Boolean = false,
    val selectedGateId: Int? = null
)
