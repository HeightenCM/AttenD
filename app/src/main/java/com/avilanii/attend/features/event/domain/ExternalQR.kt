package com.avilanii.attend.features.event.domain

import kotlinx.serialization.Serializable

@Serializable
data class ExternalQR(
    val value: String,
    val title: String
)
