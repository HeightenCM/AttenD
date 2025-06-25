package com.avilanii.attend.features.account.presentation.accountmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.account.domain.AccountMenuDataSource
import com.avilanii.attend.features.account.presentation.models.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountMenuViewModel(
    private val accountMenuDataSource: AccountMenuDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(AccountMenuState())
    val state = _state
        .onStart { loadAccountDetails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AccountMenuState()
        )

    private val _events = Channel<AccountMenuEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AccountMenuAction){
        when(action){
            is AccountMenuAction.OnLogOutClick -> {}
            is AccountMenuAction.OnNavigateClick -> {}
        }
    }

    private fun loadAccountDetails(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            accountMenuDataSource.getAccountDetails()
                .onSuccess { retrievedUser ->
                    _state.update {
                        it.copy(
                            loggedUser = User(retrievedUser.name, retrievedUser.email),
                            isLoading = false
                        )
                    }
                }
                .onError {
                        error ->
                    _events.send(AccountMenuEvent.Error(error))
                }
        }
    }
}