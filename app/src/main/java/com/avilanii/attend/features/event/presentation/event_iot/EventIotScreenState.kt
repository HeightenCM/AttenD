package com.avilanii.attend.features.event.presentation.event_iot

import com.avilanii.attend.features.event.domain.SmartGate

data class EventIotScreenState(
    val smartGates: List<SmartGate> = emptyList(),
    val isAddingSmartGate: Boolean = false,
    val isLoading: Boolean = false,
)
