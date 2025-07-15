package com.avilanii.attend.features.event.domain

import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.Result

interface EventAnnouncementsDataSource {
    suspend fun getAnnouncements(eventId: Int): Result<List<Announcement>, NetworkError>
    suspend fun postAnnouncement(eventId: Int, title: String, description: String): Result<Announcement, NetworkError>
}