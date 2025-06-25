package com.avilanii.attend.features.account.presentation.accountmenu

import com.avilanii.attend.core.domain.NetworkError

sealed interface AccountMenuEvent {
    data class Error(val error: NetworkError): AccountMenuEvent
}