package com.avilanii.attend.features.event.domain

import kotlinx.serialization.Serializable

@Serializable
data class SmartGate(
    val name: String,
    val allowedTiers: List<Pair<AttendeeTier, Int>> = emptyList()
)
