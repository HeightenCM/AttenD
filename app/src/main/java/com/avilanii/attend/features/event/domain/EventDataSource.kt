package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface EventDataSource {
    suspend fun getEvents(): Result<List<Event>, NetworkError>
    suspend fun getEvent(eventId: Int): Result<Event, NetworkError>
    suspend fun createEvent(eventName: String, eventVenue: String, eventDateTime: String): Result<Event, NetworkError>
}