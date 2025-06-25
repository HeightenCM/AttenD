package com.avilanii.attend.features.account.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.account.presentation.models.User

interface AccountMenuDataSource {
    suspend fun getAccountDetails(): Result<User, NetworkError>
}