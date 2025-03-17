package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.core.navigation.SessionManager
import com.avilanii.attend.features.event.data.mappers.toParticipant
import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantsResponseDTO
import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteParticipantDataSource(
    private val httpClient: HttpClient
): ParticipantDataSource {
    override suspend fun getParticipants(eventId: Int): Result<List<Participant>, NetworkError> {
        return safeCall<ParticipantsResponseDTO> {
            httpClient.get(
                urlString = constructUrl("events/$eventId/participants")
            ){
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken.value)
            }
        }.map { response ->
            response.data.map { it.toParticipant() }
        }
    }

    override suspend fun addParticipant(
        eventId: Int,
        name: String,
        email: String
    ): Result<Participant, NetworkError> {
        return safeCall<ParticipantDTO>{
            httpClient.post(
                urlString = constructUrl("events/$eventId/participants")
            ){
                setBody(ParticipantDTO(
                    name = name,
                    email = email,
                    eventId = eventId
                ))
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken.value)
            }
        }.map { response ->
            response.toParticipant()
        }
    }
}