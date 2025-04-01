package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.core.navigation.SessionManager
import com.avilanii.attend.features.event.data.mappers.toEvent
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventsResponseDTO
import com.avilanii.attend.features.event.domain.AttendingEventDataSource
import com.avilanii.attend.features.event.domain.Event
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteAttendingEventDataSource(
    private val httpClient: HttpClient
): AttendingEventDataSource {
    override suspend fun getEvents(): Result<List<Event>, NetworkError> {
        return safeCall<EventsResponseDTO> {
            httpClient.get(
                urlString = constructUrl("/events/attending")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken.value)
            }
        }.map { response ->
            response.data.map{ it.toEvent() }
        }
    }

    override suspend fun addEvent(qrValue: Int): Result<Event, NetworkError> {
        return safeCall<EventDTO> {
            httpClient.post(
                urlString = constructUrl("/events/attending")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken.value)
            }
        }.map { response ->
            response.toEvent()
        }
    }

    override suspend fun respondEvent(
        eventId: Int,
        isAccepted: Boolean
    ): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = constructUrl("events/attending/${eventId}")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken.value)
                setBody(isAccepted)
            }
        }
    }
}