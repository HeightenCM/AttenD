package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface EventAnalyticsDataSource {
    suspend fun getEventTierDistribution(eventId: Int): Result<List<Pair<String, Int>>, NetworkError>
}