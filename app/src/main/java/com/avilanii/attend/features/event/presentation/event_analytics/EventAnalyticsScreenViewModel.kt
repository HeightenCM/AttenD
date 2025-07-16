package com.avilanii.attend.features.event.presentation.event_analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.EventAnalyticsDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventAnalyticsScreenViewModel(
    private val eventAnalyticsDataSource: EventAnalyticsDataSource,
    private val eventId: Int
): ViewModel() {
    private val _state = MutableStateFlow(EventAnalyticsScreenState())
    val state = _state
        .onStart {
            loadEventTierDistribution()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventAnalyticsScreenState()
        )

    private val _events = Channel< EventAnalyticsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: EventAnalyticsScreenAction){
        when(action){
            is EventAnalyticsScreenAction.OnMenuIconClick -> {}
            is EventAnalyticsScreenAction.OnTierDistributionPieClick -> loadEventTierDistribution()
        }
    }

    private fun loadEventTierDistribution() {
        viewModelScope.launch {
            eventAnalyticsDataSource
                .getEventTierDistribution(eventId)
                .onSuccess { tierDistribution ->
                    _state.update {
                        it.copy(
                            eventTierDistribution = tierDistribution,
                            isShowingTierDistributionPie = true
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(EventAnalyticsEvent.Error(error))
                }
        }
    }
}