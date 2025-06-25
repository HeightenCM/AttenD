package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.EventIotDataSource
import com.avilanii.attend.features.event.domain.SmartGate

class RemoteEventIotDataSource: EventIotDataSource {
    override suspend fun getIotDevices(eventId: Int): Result<List<SmartGate>, NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun addSmartGate(eventId: Int, name: String): Result<Unit, NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun removeGateTier(
        eventId: Int,
        name: String,
        attendeeTier: AttendeeTier
    ): Result<Unit, NetworkError> {
        TODO("Not yet implemented")
    }

    override suspend fun addGateTier(
        eventId: Int,
        name: String,
        attendeeTier: AttendeeTier
    ): Result<Unit, NetworkError> {
        TODO("Not yet implemented")
    }
}