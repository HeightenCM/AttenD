package com.avilanii.attend.features.auth.presentation.models

import com.avilanii.attend.features.auth.domain.UserLoginRequest

data class UserLoginRequestUi(
    val email: String,
    val password: String
)

fun UserLoginRequest.toUserLoginRequestUi(): UserLoginRequestUi{
    return UserLoginRequestUi(
        email = email.value,
        password = password,
    )
}
