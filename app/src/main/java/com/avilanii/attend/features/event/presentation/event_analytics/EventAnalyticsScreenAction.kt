package com.avilanii.attend.features.event.presentation.event_analytics

sealed interface EventAnalyticsScreenAction {
    data object OnMenuIconClick: EventAnalyticsScreenAction
    data object OnTierDistributionPieClick: EventAnalyticsScreenAction
    data object OnParticipantStatusDistributionClick: EventAnalyticsScreenAction
}