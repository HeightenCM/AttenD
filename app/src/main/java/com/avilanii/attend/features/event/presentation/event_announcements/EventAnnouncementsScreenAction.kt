package com.avilanii.attend.features.event.presentation.event_announcements

sealed interface EventAnnouncementsScreenAction {
    data object OnMenuIconClick: EventAnnouncementsScreenAction
    data object OnPostAnnouncementClick: EventAnnouncementsScreenAction
    data object OnDismissPostAnnouncementDialog: EventAnnouncementsScreenAction
    data class OnPostedAnnouncementClick(val title: String, val description: String): EventAnnouncementsScreenAction
}