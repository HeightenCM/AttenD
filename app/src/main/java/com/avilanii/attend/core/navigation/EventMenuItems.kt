package com.avilanii.attend.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.DeviceHub
import androidx.compose.material.icons.outlined.NotificationImportant
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class EventMenuItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val route: EventMenuRoutes
) {
    Participants(Icons.Filled.PersonAdd,
        Icons.Outlined.PersonAdd,
        "Participants",
        EventMenuRoutes.Participants
    ),
    Analytics(Icons.Filled.Analytics,
        Icons.Outlined.Analytics,
        "Analytics",
        EventMenuRoutes.Participants
    ),
    Iot(Icons.Filled.DeviceHub,
        Icons.Outlined.DeviceHub,
        "IoT",
        EventMenuRoutes.IoT
    ),
    Announcements(Icons.Filled.NotificationImportant,
        Icons.Outlined.NotificationImportant,
        "Announcements",
        EventMenuRoutes.Announcements
    )
}

sealed interface EventMenuRoutes{
    @Serializable
    data object Participants: EventMenuRoutes
    @Serializable
    data object Analytics: EventMenuRoutes
    @Serializable
    data object IoT: EventMenuRoutes
    @Serializable
    data object Announcements: EventMenuRoutes
}