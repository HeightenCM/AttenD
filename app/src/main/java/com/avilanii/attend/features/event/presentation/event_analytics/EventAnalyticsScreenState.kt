package com.avilanii.attend.features.event.presentation.event_analytics

data class EventAnalyticsScreenState(
    val eventTierDistribution: List<Pair<String, Int>> = emptyList(),
    val isLoading: Boolean = false,
    val isShowingTierDistributionPie: Boolean = false
)
