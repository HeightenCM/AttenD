package com.avilanii.attend.features.auth.presentation.models

import com.avilanii.attend.features.auth.domain.UserRegisterRequest

data class UserRegisterRequestUi(
    val name: String,
    val email: String,
    val password: String
)

fun UserRegisterRequest.toUserRegisterRequestUi(): UserRegisterRequestUi{
    return UserRegisterRequestUi(
        name = name,
        email = email.value,
        password = password
    )
}