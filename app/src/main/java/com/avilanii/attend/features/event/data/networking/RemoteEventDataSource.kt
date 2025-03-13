package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.features.event.data.mappers.toEvent
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventsResponseDTO
import com.avilanii.attend.features.event.domain.Event
import com.avilanii.attend.features.event.domain.EventDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class RemoteEventDataSource(
    private val httpClient: HttpClient
) : EventDataSource {
    override suspend fun getEvents(): Result<List<Event>, NetworkError> {
        return safeCall<EventsResponseDTO> {
            httpClient.get(
                urlString = constructUrl("/events")
            )
        }.map { response ->
            response.data.map { it.toEvent() }
        }
    }

    override suspend fun getEvent(eventId: Int): Result<Event, NetworkError> {
        return safeCall<EventDTO> {
            httpClient.get(
                urlString = constructUrl("/events/$eventId")
            )
        }.map { response ->
            response.toEvent()
        }
    }

    override suspend fun createEvent(
        eventName: String,
        eventBudget: Int,
        eventDateTime: String
    ): Result<Event, NetworkError> {
        return safeCall<EventDTO> {
            httpClient.post(
                urlString = constructUrl("/events")
            ) {
                setBody(EventDTO(
                    name = eventName,
                    budget = eventBudget,
                    dateTime = eventDateTime
                ))
            }
        }.map { response ->
            response.toEvent()
        }
    }
}