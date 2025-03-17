package com.avilanii.attend.features.auth.data.networking.datatransferobjects

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequestDTO(
    val name: String,
    val email: String,
    val password: String
)