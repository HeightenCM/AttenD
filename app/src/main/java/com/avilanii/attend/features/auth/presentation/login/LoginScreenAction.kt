package com.avilanii.attend.features.auth.presentation.login

import com.avilanii.attend.features.auth.presentation.models.UserLoginRequestUi

interface LoginScreenAction {
    data class OnSubmitForm(val userLoginRequestUi: UserLoginRequestUi): LoginScreenAction
    data object OnRegisterClick:  LoginScreenAction
}