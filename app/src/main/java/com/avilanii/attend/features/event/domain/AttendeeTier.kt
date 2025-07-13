package com.avilanii.attend.features.event.domain

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeTier(
    val title: String,
    val count: Int? = null,
    val isAllowed: Boolean? = null
)
