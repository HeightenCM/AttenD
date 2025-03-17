package com.avilanii.attend.features.auth.presentation.register

import com.avilanii.attend.features.auth.presentation.models.UserRegisterRequestUi

interface RegisterScreenAction {
    data class OnSubmitForm(val userRegisterRequestUi: UserRegisterRequestUi): RegisterScreenAction
    data object OnLoginClick: RegisterScreenAction
}