package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface EventIotDataSource {
    suspend fun getIotDevices(eventId: Int): Result<List<SmartGate>, NetworkError>
    suspend fun addSmartGate(eventId: Int, name: String): Result<String, NetworkError>
    suspend fun removeGateTier(eventId: Int, name: String, attendeeTier: AttendeeTier): Result<Unit, NetworkError>
    suspend fun addGateTier(eventId: Int, name: String, attendeeTier: AttendeeTier): Result<Unit, NetworkError>
    suspend fun loadEventTiers(eventId: Int): Result<List<AttendeeTier>, NetworkError>
}