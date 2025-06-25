package com.avilanii.attend.features.account.data

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.account.domain.AccountMenuDataSource
import com.avilanii.attend.features.account.presentation.models.User
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class RemoteAccountMenuDataSource(
    private val httpClient: HttpClient
) : AccountMenuDataSource {
    override suspend fun getAccountDetails(): Result<User, NetworkError> {
        return safeCall<User> {
            httpClient.get(
                urlString = constructUrl("users")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }
}