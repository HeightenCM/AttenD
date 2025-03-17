package com.avilanii.attend.features.auth.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface AuthDataSource {
    suspend fun register(name: String, email: String, password: String) : Result<Int, NetworkError>
    suspend fun login(email: String, password: String) : Result<String, NetworkError>
}