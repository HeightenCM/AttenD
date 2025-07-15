package com.avilanii.attend.features.event.data.networking.datatransferobjects

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.features.event.data.mappers.toAnnouncement
import com.avilanii.attend.features.event.domain.Announcement
import com.avilanii.attend.features.event.domain.EventAnnouncementsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteEventAnnouncementDataSource(
    private val httpClient: HttpClient
): EventAnnouncementsDataSource {
    override suspend fun getAnnouncements(eventId: Int): Result<List<Announcement>, NetworkError> {
        return safeCall<List<AnnouncementDTO>> {
            httpClient.get(
                urlString = constructUrl("events/${eventId}/announcements")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }.map { response ->
            response.map { it.toAnnouncement() }
        }
    }

    override suspend fun postAnnouncement(
        eventId: Int,
        title: String,
        description: String
    ): Result<Announcement, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
                setBody(
                    AnnouncementDTO(
                        id = -1,
                        title = title,
                        description = description,
                        dateTime = ""
                    )
                )
            }
        }
    }
}