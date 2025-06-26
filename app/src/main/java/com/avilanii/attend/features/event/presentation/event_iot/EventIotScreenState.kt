package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.SmartGate

data class EventIotScreenState(
    val smartGates: List<SmartGate> = emptyList(),
    val isAddingSmartGate: Boolean = false,
    val isSmartGateAdded: Boolean = false,
    val isLoading: Boolean = false,
    val isAddingGateTier: Boolean = false,
    val tiers: List<AttendeeTier> = emptyList(),
    val selectedGate: SmartGate? = null,
)
