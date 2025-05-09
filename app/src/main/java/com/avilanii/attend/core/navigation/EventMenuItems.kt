package com.avilanii.attend.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

enum class EventMenuItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val route: EventMenuRoutes
) {
    Participants(Icons.Filled.Person,
        Icons.Outlined.Person,
        "Participants",
        EventMenuRoutes.Participants),
    Statistics(Icons.Filled.Info,
        Icons.Outlined.Info,
        "Statistics",
        EventMenuRoutes.Participants),

}

sealed interface EventMenuRoutes{
    @Serializable
    data object Participants: EventMenuRoutes
}