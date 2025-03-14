package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface ParticipantDataSource {
    suspend fun getParticipants(): Result<List<Participant>, NetworkError>
    suspend fun addParticipant(name: String, email: String): Result<Participant, NetworkError>
}