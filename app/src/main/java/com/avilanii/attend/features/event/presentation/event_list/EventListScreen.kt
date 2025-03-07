package com.avilanii.attend.features.event.presentation.event_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.presentation.event_list.components.CreateEventFAB
import com.avilanii.attend.features.event.presentation.event_list.components.EventListItem
import com.avilanii.attend.features.event.presentation.event_list.components.previewEvent
import com.avilanii.attend.features.event.presentation.models.toDisplayableDate
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    modifier: Modifier = Modifier,
    state: EventListState,
    onAction: (EventListAction)->Unit) {
    var showCreateEventDialog by remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Events") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            CreateEventFAB {
                onAction(EventListAction.OnCreateEventClick)
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
            if (showCreateEventDialog){

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
                    previewEvent.copy(id = it, dateTime = LocalDateTime.now().plusDays(1).toDisplayableDate())
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {}
        )
    }
}