package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO

interface ParticipantDataSource {
    suspend fun getParticipants(eventId: Int): Result<List<Participant>, NetworkError>
    suspend fun addParticipant(eventId: Int, name: String, email: String): Result<Participant, NetworkError>
    suspend fun generateQRInvite(eventId: Int): Result<String, NetworkError>
    suspend fun scanParticipantQr(eventId: Int, eventQr: String): Result<CheckInConfirmationDTO, NetworkError>
    suspend fun getEventTiers(eventId: Int): Result<List<AttendeeTier>, NetworkError>
    suspend fun addEventTier(attendeeTier: String, eventId: Int): Result<Int, NetworkError>
    suspend fun removeEventTier(tierId: Int, eventId: Int): Result<Unit, NetworkError>
    suspend fun assignParticipantTier(eventId: Int, participantId: Int, tierId: Int): Result<String, NetworkError>
    suspend fun resignParticipantTier(eventId: Int, participantId: Int): Result<Unit, NetworkError>
}