package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.core.domain.map
import com.avilanii.attend.features.event.data.mappers.toParticipant
import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantDTO
import com.avilanii.attend.features.event.data.networking.datatransferobjects.ParticipantsResponseDTO
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
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
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
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
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }.map { response ->
            response.toParticipant()
        }
    }

    override suspend fun generateQRInvite(eventId: Int): Result<String, NetworkError> {
        return safeCall<String> {
            httpClient.get(
                urlString = constructUrl("qr/invite/${eventId}")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }

    override suspend fun scanParticipantQr(eventId: Int,eventQr: String): Result<CheckInConfirmationDTO, NetworkError> {
        return safeCall<CheckInConfirmationDTO> {
            httpClient.get(
                urlString = constructUrl("events/$eventId/participants/checkin")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(eventQr)
            }
        }
    }

    override suspend fun getEventTiers(eventId: Int): Result<List<AttendeeTier>, NetworkError> {
        return safeCall<List<AttendeeTier>> {
            httpClient.get(
                urlString = constructUrl("/events/$eventId/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }

    override suspend fun addEventTier(attendeeTier: AttendeeTier, eventId: Int): Result<Unit, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("events/$eventId/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(attendeeTier)
            }
        }
    }

    override suspend fun removeEventTier(
        attendeeTier: AttendeeTier,
        eventId: Int
    ): Result<Unit, NetworkError> {
        return safeCall {
            httpClient.delete(
                urlString = constructUrl("events/$eventId/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(attendeeTier)
            }
        }
    }

    override suspend fun assignParticipantTier(
        participant: ParticipantDTO,
        attendeeTier: AttendeeTier
    ): Result<Unit, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("events/${participant.eventId}/participants/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(Pair(participant, attendeeTier))
            }
        }
    }

    override suspend fun resignParticipantTier(participant: ParticipantDTO): Result<Unit, NetworkError> {
        return safeCall {
            httpClient.delete(
                urlString = constructUrl("events/${participant.eventId}/participants/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
                setBody(participant)
            }
        }
    }
}