package com.avilanii.attend.features.event.data.mappers

import com.avilanii.attend.features.event.data.networking.datatransferobjects.AnnouncementDTO
import com.avilanii.attend.features.event.domain.Announcement
import java.time.LocalDateTime

fun AnnouncementDTO.toAnnouncement(): Announcement{
    return Announcement(
        id = id,
        title = title,
        description = description,
        dateTime = LocalDateTime.parse(dateTime)
    )
}