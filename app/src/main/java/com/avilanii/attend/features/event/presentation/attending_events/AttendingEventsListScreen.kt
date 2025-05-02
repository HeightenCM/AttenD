package com.avilanii.attend.features.event.presentation.attending_events

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import com.avilanii.attend.features.event.presentation.attending_events.components.AttendingEventsListItem
import com.avilanii.attend.features.event.presentation.attending_events.components.EventParticipationInterogation
import com.avilanii.attend.features.event.presentation.attending_events.components.EventQrDialog
import com.avilanii.attend.features.event.presentation.event_list.components.previewEvent
import com.avilanii.attend.features.event.presentation.models.toDisplayableDateTime
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendingEventsListScreen(
    modifier: Modifier = Modifier,
    state: AttendingEventsListState,
    bottomNavBarItems: List<BottomNavigationItem>,
    onAction: (AttendingEventsListAction) -> Unit
    ) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Attending events") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {onAction(AttendingEventsListAction.OnAddEventQrClick)},
                icon = { Icon(Icons.Filled.Add, "Add attending event icon") },
                text = { Text("Add event") },
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == 1,
                        onClick = {onAction(AttendingEventsListAction.OnNavigateClick(index))},
                        icon = {
                            Icon(
                                imageVector = if (index == 1)
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
                    AttendingEventsListItem (
                        modifier = modifier.fillMaxWidth(),
                        eventUi = eventUi
                    ) {
                        if(eventUi.isPending == false)
                            onAction(AttendingEventsListAction.OnEventClick(eventUi))
                    }
                    if (eventUi.isPending == true)
                        EventParticipationInterogation(
                            onAccept = { onAction(AttendingEventsListAction.OnAcceptEventInvitationClick(eventUi.id)) },
                            onReject = { onAction(AttendingEventsListAction.OnRejectEventInvitationClick(eventUi.id)) }
                        )
                    HorizontalDivider()
                }
            }
            if (state.isInspectingEvent && state.selectedEvent!= null && state.selectedQr!=null){
                EventQrDialog(
                    eventUi = state.selectedEvent,
                    qrCode = state.selectedQr,
                ) {
                    onAction(AttendingEventsListAction.OnDismissEventInspectDialog)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAttendingEventsListScreen() {
    AttenDTheme {
        AttendingEventsListScreen(
            state = AttendingEventsListState(
                events = (1..50).map {
                    previewEvent.copy(id = it,
                        dateTime = LocalDateTime.now().plusMinutes(1).toDisplayableDateTime(),
                        isPending = it%2==0)
                }
            ),
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
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}