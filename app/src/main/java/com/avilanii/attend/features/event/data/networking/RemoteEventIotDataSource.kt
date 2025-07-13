package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.EventIotDataSource
import com.avilanii.attend.features.event.domain.SmartGate
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class RemoteEventIotDataSource(
    private val httpClient: HttpClient
): EventIotDataSource {
    override suspend fun getIotDevices(eventId: Int): Result<List<SmartGate>, NetworkError> {
        return safeCall<List<SmartGate>> {
            httpClient.get(
                urlString = constructUrl("events/${eventId}/smartGates")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }
    }

    override suspend fun addSmartGate(eventId: Int, name: String): Result<SmartGate, NetworkError> {
        return safeCall {
            httpClient.post(
                urlString = constructUrl("events/${eventId}/smartGates")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
                setBody(name)
            }
        }
    }

    override suspend fun activateSmartGate(
        eventId: Int,
        gateId: Int
    ): Result<String, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("events/${eventId}/smartGates/${gateId}/activate")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }
    }

    override suspend fun changeGateTierState(
        eventId: Int,
        gateId: Int,
        tierId: Int
    ): Result<Unit, NetworkError> {
        return safeCall {
            httpClient.put(
                urlString = constructUrl("events/${eventId}/smartGates/${gateId}/tiers/${tierId}")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }
    }

    override suspend fun loadEventTiers(
        eventId: Int,
        gateId: Int
    ): Result<List<AttendeeTier>, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("events/${eventId}/smartGates/${gateId}/tiers")
            ) {
                header(HttpHeaders.Authorization, "Bearer "+ SessionManager.jwtToken)
            }
        }
    }
}