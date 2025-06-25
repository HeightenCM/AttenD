package com.avilanii.attend.features.account.presentation.accountmenu

sealed interface AccountMenuAction {
    data object OnLogOutClick: AccountMenuAction
    data class OnNavigateClick(val index: Int): AccountMenuAction
}