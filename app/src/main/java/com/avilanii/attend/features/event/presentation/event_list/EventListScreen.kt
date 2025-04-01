package com.avilanii.attend.features.event.presentation.event_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.avilanii.attend.core.navigation.BottomNavigationItem
import com.avilanii.attend.features.event.presentation.event_list.components.CreateEventDialog
import com.avilanii.attend.features.event.presentation.event_list.components.CreateEventFAB
import com.avilanii.attend.features.event.presentation.event_list.components.EventListItem
import com.avilanii.attend.features.event.presentation.event_list.components.previewEvent
import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.features.event.presentation.models.toDisplayableDateTime
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    modifier: Modifier = Modifier,
    state: EventListState,
    bottomNavBarItems: List<BottomNavigationItem>,
    onAction: (EventListAction)->Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Organizing events") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            CreateEventFAB {
                onAction(EventListAction.OnCreateEventClick)
            }
        },
        bottomBar = {
            NavigationBar {
                bottomNavBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == 0,
                        onClick = {onAction(EventListAction.OnNavigateClick(index))},
                        icon = {
                            Icon(
                                imageVector = if (index == 0)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {Text(item.title)}
                    )
                }
            }

        }
    ){ paddingValues ->
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
                items(state.events) { eventUi ->
                    EventListItem(
                        eventUi,
                        onClick = { onAction(EventListAction.OnEventClick(eventUi)) },
                        modifier = modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
            if (state.isCreatingEvent){
                CreateEventDialog(
                    onDismiss = {
                        onAction(EventListAction.OnDismissCreateEventDialog)
                    },
                    onSubmit = { eventName, eventBudget, eventDate, eventTime ->
                        onAction(EventListAction.OnCreatedEvent(eventName, eventBudget, eventDate, eventTime))
                    },
                    eventData = EventUi(),
                    modifier = modifier
                )
            }
        }
    }


}

@PreviewLightDark
@Composable
private fun EventListScreenPreview() {
    AttenDTheme {
        EventListScreen(
            state = EventListState(
                events = (1..50).map {
                    previewEvent.copy(id = it, dateTime = LocalDateTime.now().plusMinutes(1).toDisplayableDateTime())
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            bottomNavBarItems = listOf(
                BottomNavigationItem(
                    title = "Organizing",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                BottomNavigationItem(
                    title = "Attending",
                    selectedIcon = Icons.Filled.Email,
                    unselectedIcon = Icons.Outlined.Email
                ),
                BottomNavigationItem(
                    title = "Account",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle
                ),
            ),
            onAction = {}
        )
    }
}