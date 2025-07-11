package com.avilanii.attend.features.event.presentation.event_analytics

import com.avilanii.attend.features.event.domain.AttendeeTier

data class EventAnalyticsScreenState(
    val eventTierDistribution: List<Pair<AttendeeTier, Int>> = emptyList(),
    val isLoading: Boolean = false
)
