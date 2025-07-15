package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.features.event.data.mappers.toAnnouncement
import com.avilanii.attend.features.event.data.mappers.toEvent
import com.avilanii.attend.features.event.data.networking.datatransferobjects.AnnouncementDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.EventsResponseDTO
import com.avilanii.attend.features.event.domain.Announcement
import com.avilanii.attend.features.event.domain.AttendingEventDataSource
import com.avilanii.attend.features.event.domain.Event
import com.avilanii.attend.features.event.domain.ExternalQR
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
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }.map { response ->
            response.data.map{ it.toEvent() }
        }
    }

    override suspend fun addEvent(qrValue: String): Result<Event, NetworkError> {
        return safeCall<EventDTO> {
            httpClient.post(
                urlString = constructUrl("/events/attending")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(qrValue)
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
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(isAccepted)
            }
        }
    }

    override suspend fun getQr(eventId: Int): Result<String, NetworkError> {
        return safeCall<String> {
            httpClient.get(
                urlString = constructUrl("qr/${eventId}")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }

    override suspend fun addExternalQr(externalQR: ExternalQR): Result<ExternalQR, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("qr")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(externalQR)
            }
        }
    }

    override suspend fun getExternalQrs(): Result<List<ExternalQR>, NetworkError> {
        return safeCall<List<ExternalQR>> {
            httpClient.get(
                urlString = constructUrl("qr")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }

    override suspend fun getAnnouncements(eventId: Int): Result<List<Announcement>, NetworkError> {
        return safeCall<List<AnnouncementDTO>> {
            httpClient.get(
                constructUrl("events/attending/${eventId}/announcements")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }.map { announcements ->
            announcements.map { it.toAnnouncement() }
        }
    }
}