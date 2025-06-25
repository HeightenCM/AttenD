package com.avilanii.attend.features.account.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String
)
