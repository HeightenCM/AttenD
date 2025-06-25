package com.avilanii.attend.features.account.presentation.accountmenu

import com.avilanii.attend.features.account.presentation.models.User

data class AccountMenuState(
    val loggedUser: User? = null,
    val isLoading: Boolean = false
)
