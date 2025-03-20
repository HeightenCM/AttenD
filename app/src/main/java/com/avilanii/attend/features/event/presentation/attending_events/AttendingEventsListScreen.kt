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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.avilanii.attend.features.event.presentation.attending_events.components.AttendingEventsListItem
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
                        onAction(AttendingEventsListAction.OnEventClick(eventUi.id))
                    }
                    HorizontalDivider()
                }
            }
            if (state.isInspectingEvent){
                EventQrDialog(
                    eventUi = state.selectedEvent!!,
                    qrCode = state.selectedEventQrCode!!
                ) {
                    onAction(AttendingEventsListAction.OnDismissAddEventQrDialog)
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
                    previewEvent.copy(id = it, dateTime = LocalDateTime.now().plusMinutes(1).toDisplayableDateTime())
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}