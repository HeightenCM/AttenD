package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface AttendingEventDataSource {
    suspend fun getEvents(): Result<List<Event>, NetworkError>
    suspend fun addEvent(qrValue: Int): Result<Event, NetworkError>
}