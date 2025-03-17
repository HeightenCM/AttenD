package com.avilanii.attend.features.auth.data.networking

import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.auth.data.networking.datatransferobjects.UserLoginRequestDTO
import com.avilanii.attend.features.auth.data.networking.datatransferobjects.UserRegisterRequestDTO
import com.avilanii.attend.features.auth.domain.AuthDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RemoteAuthDataSource(
    private val httpClient: HttpClient
) : AuthDataSource {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Int, NetworkError> {
        return safeCall<Int> {
            httpClient.post(
                urlString = constructUrl("/register")
            ) {
                setBody(UserRegisterRequestDTO(
                    name = name,
                    email = email,
                    password = password
                ))
            }
        }
    }

    override suspend fun login(email: String, password: String): Result<String, NetworkError> {
        return safeCall<String> {
            httpClient.post(
                urlString = constructUrl("/login")
            ) {
                setBody(UserLoginRequestDTO(
                    email = email,
                    password = password
                ))
            }
        }
    }
}