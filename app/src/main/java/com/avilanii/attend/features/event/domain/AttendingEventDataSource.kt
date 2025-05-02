package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.event.presentation.models.EventUi

interface AttendingEventDataSource {
    suspend fun getEvents(): Result<List<Event>, NetworkError>
    suspend fun addEvent(qrValue: Int): Result<Event, NetworkError>
    suspend fun respondEvent(eventId: Int, isAccepted: Boolean): Result<Unit, NetworkError>
    suspend fun getQr(eventId: Int): Result<String, NetworkError>
    suspend fun addQr(eventUi: EventUi, qrValue: Long): Result<Unit, NetworkError>
}