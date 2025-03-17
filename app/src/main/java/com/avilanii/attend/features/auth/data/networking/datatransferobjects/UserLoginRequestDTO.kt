package com.avilanii.attend.features.auth.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequestDTO(
    val email: String,
    val password: String
)