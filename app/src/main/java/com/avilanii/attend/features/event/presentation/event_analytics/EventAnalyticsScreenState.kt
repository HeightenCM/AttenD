package com.avilanii.attend.features.event.presentation.event_analytics

data class EventAnalyticsScreenState(
    val pieValues: List<Pair<String, Int>> = emptyList(),
    val isLoading: Boolean = false,
    val isShowingPie: Boolean = false
)
