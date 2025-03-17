package com.avilanii.attend.features.auth.domain

import com.avilanii.attend.core.domain.models.Email

data class UserRegisterRequest(
    val name: String,
    val email: Email,
    val password: String
)
