package com.avilanii.attend.features.event.presentation.event_announcements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.domain.Announcement
import com.avilanii.attend.features.event.presentation.event_announcements.components.EventAnnouncementListItem
import com.avilanii.attend.features.event.presentation.event_announcements.components.PostAnnouncementDialog
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventAnnouncementsScreen(modifier: Modifier = Modifier,
                         state: EventAnnouncementsScreenState,
                         onAction: (EventAnnouncementsScreenAction) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {onAction(EventAnnouncementsScreenAction.OnMenuIconClick)}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open event menu")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {onAction(EventAnnouncementsScreenAction.OnPostAnnouncementClick)},
                icon = { Icon(Icons.Filled.Add, "Post announcement") },
                text = { Text("Post announcement") },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = modifier
            )
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.announcements) { announcement ->
                    EventAnnouncementListItem(
                        announcement = announcement
                    )
                }
            }
        }
        if (state.isPostingAnnouncement){
            PostAnnouncementDialog(
                onDismiss = {
                    onAction(EventAnnouncementsScreenAction.OnDismissPostAnnouncementDialog)
                }
            ) { title, description ->
                onAction(EventAnnouncementsScreenAction.OnPostedAnnouncementClick(title, description))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewEventAnalyticsScreen() {
    AttenDTheme {
        EventAnnouncementsScreen(
            state = EventAnnouncementsScreenState(
                announcements = listOf(
                    Announcement(
                        id = 1,
                        title = "Important notice",
                        description = "The event will not take place at Str. 1 Brasov anymore. We are sorry.",
                        dateTime = LocalDateTime.now().minusDays(1)
                    ),
                    Announcement(
                        id = 1,
                        title = "Important notice #2",
                        description = "The event will actually take place at Str. 1 Brasov. We made a mistake in the last announcement. Sorry again.",
                        dateTime = LocalDateTime.now()
                    )
                )
            ),
            onAction = {}
        )
    }
}