package com.avilanii.attend.features.event.presentation.event_announcements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.domain.EventAnnouncementsDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventAnnouncementsScreenViewModel(
    private val eventAnnouncementsDataSource: EventAnnouncementsDataSource,
    private val eventId: Int
): ViewModel() {
    private val _state = MutableStateFlow(EventAnnouncementsScreenState())
    val state = _state
        .onStart {
            loadAnnouncements()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            EventAnnouncementsScreenState()
        )

    private val _events = Channel< EventAnnouncementsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: EventAnnouncementsScreenAction){
        when(action){
            is EventAnnouncementsScreenAction.OnMenuIconClick -> {}
            is EventAnnouncementsScreenAction.OnPostAnnouncementClick -> _state.update {
                it.copy(
                    isPostingAnnouncement = true
                )
            }
            is EventAnnouncementsScreenAction.OnDismissPostAnnouncementDialog -> _state.update {
                it.copy(
                    isPostingAnnouncement = false
                )
            }
            is EventAnnouncementsScreenAction.OnPostedAnnouncementClick -> postAnnouncement(action.title, action.description)
        }
    }

    private fun loadAnnouncements() {
        viewModelScope.launch {
            eventAnnouncementsDataSource
                .getAnnouncements(eventId)
                .onSuccess { announcements ->
                    _state.update {
                        it.copy(
                            announcements = announcements
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(EventAnnouncementsEvent.Error(error))
                }
        }
    }

    private fun postAnnouncement(title: String, description: String){
        _state.update {
            it.copy(
                isPostingAnnouncement = false
            )
        }
        viewModelScope.launch {
            eventAnnouncementsDataSource
                .postAnnouncement(eventId, title, description)
                .onSuccess {  receivedAnnouncement ->
                    _state.update {
                        it.copy(
                            announcements = it.announcements + receivedAnnouncement
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(EventAnnouncementsEvent.Error(error))
                }
        }
    }
}