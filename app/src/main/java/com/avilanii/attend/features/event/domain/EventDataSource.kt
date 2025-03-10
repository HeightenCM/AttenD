package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import java.time.LocalDateTime

interface EventDataSource {
    suspend fun getEvents(): Result<List<Event>, NetworkError>
    suspend fun getEvent(eventId: Int): Result<Event, NetworkError>
    suspend fun createEvent(eventName: String, eventBudget: Int, eventDateTime: LocalDateTime): Result<Event, NetworkError>
}