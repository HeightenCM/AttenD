package com.avilanii.attend.features.event.data.networking

import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.constructUrl
import com.avilanii.attend.core.data.safeCall
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result
import com.avilanii.attend.features.event.domain.EventAnalyticsDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class RemoteEventAnalyticsDataSource(
    private val httpClient: HttpClient
): EventAnalyticsDataSource {
    override suspend fun getEventTierDistribution(eventId: Int): Result<List<Pair<String, Int>>, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("/events/${eventId}/analytics/tierPie")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }

    override suspend fun getParticipantStatusDistribution(eventId: Int): Result<List<Pair<String, Int>>, NetworkError> {
        return safeCall {
            httpClient.get(
                urlString = constructUrl("/events/${eventId}/analytics/statusPie")
            ) {
                header(HttpHeaders.Authorization, "Bearer " + SessionManager.jwtToken)
            }
        }
    }
}