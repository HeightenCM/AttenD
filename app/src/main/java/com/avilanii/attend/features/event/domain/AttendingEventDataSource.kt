package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface AttendingEventDataSource {
    suspend fun getEvents(): Result<List<Event>, NetworkError>
    suspend fun addEvent(qrValue: String): Result<Event, NetworkError>
    suspend fun respondEvent(eventId: Int, isAccepted: Boolean): Result<Unit, NetworkError>
    suspend fun getQr(eventId: Int): Result<String, NetworkError>
    suspend fun addExternalQr(externalQR: ExternalQR): Result<ExternalQR, NetworkError>
    suspend fun getExternalQrs(): Result<List<ExternalQR>, NetworkError>
}