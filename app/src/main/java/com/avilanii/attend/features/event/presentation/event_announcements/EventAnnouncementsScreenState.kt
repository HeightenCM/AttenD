package com.avilanii.attend.features.event.presentation.event_announcements

import com.avilanii.attend.features.event.domain.Announcement

data class EventAnnouncementsScreenState(
    val announcements: List<Announcement> = emptyList(),
    val isLoading: Boolean = false,
    val isPostingAnnouncement: Boolean = false
)
