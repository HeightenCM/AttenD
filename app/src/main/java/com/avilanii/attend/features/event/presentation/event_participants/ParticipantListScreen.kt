package com.avilanii.attend.features.event.presentation.event_participants

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
import com.avilanii.attend.features.event.domain.ParticipantStatus
import com.avilanii.attend.features.event.presentation.event_participants.components.AddParticipantDialog
import com.avilanii.attend.features.event.presentation.event_participants.components.ParticipantListItem
import com.avilanii.attend.features.event.presentation.event_participants.components.previewParticipant
import com.avilanii.attend.ui.theme.AttenDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantListScreen(
    state: ParticipantListState,
    modifier: Modifier = Modifier,
    onAction: (ParticipantListAction)->Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Participants") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {onAction(ParticipantListAction.OnAddParticipantClick)},
                icon = { Icon(Icons.Filled.Add, "Add participant icon") },
                text = { Text("Add participant") },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = modifier
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
                items(state.participants) { participantUi ->
                    ParticipantListItem(
                        participantUi = participantUi,
                        onClick = {onAction(ParticipantListAction.OnParticipantClick(participantUi))},
                        modifier = modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
            if (state.isAddingParticipant){
                AddParticipantDialog(
                    onDismiss = {
                        onAction(ParticipantListAction.OnDismissAddParticipantDialog)
                    },
                    onSubmit = { name, email ->
                        onAction(ParticipantListAction.OnAddedParticipant(name, email))
                    },
                    modifier = modifier
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewParticipantsListScreen() {
    AttenDTheme {
        ParticipantListScreen(
            state = ParticipantListState(
                participants = (1..10).map{
                    previewParticipant.copy()
                } + (1..10).map {
                    previewParticipant.copy(status = ParticipantStatus.PENDING)
                } + (1..10).map {
                    previewParticipant.copy(status = ParticipantStatus.REJECTED)
                }
            ),
            onAction = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}