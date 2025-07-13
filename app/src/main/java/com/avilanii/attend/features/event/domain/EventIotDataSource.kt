package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface EventIotDataSource {
    suspend fun getIotDevices(eventId: Int): Result<List<SmartGate>, NetworkError>
    suspend fun addSmartGate(eventId: Int, name: String): Result<SmartGate, NetworkError>
    suspend fun activateSmartGate(eventId: Int, gateId: Int): Result<String, NetworkError>
    suspend fun changeGateTierState(eventId: Int, gateId: Int, tierId: Int): Result<Unit, NetworkError>
    suspend fun loadEventTiers(eventId: Int, gateId: Int): Result<List<AttendeeTier>, NetworkError>
}