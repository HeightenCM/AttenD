package com.avilanii.attend.features.event.domain

import kotlinx.serialization.Serializable

@Serializable
data class SmartGate(
    val id: Int,
    val name: String,
    val isOnline: Boolean
)
