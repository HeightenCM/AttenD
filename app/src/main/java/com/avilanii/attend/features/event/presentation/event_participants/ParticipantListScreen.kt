package com.avilanii.attend.features.event.presentation.event_participants

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.avilanii.attend.features.event.domain.ParticipantStatus
import com.avilanii.attend.features.event.presentation.event_participants.components.AddParticipantDialog
import com.avilanii.attend.features.event.presentation.event_participants.components.AttendeeTiersDialog
import com.avilanii.attend.features.event.presentation.event_participants.components.CheckInReviewDialog
import com.avilanii.attend.features.event.presentation.event_participants.components.EventInviteQRDialog
import com.avilanii.attend.features.event.presentation.event_participants.components.ParticipantListItem
import com.avilanii.attend.features.event.presentation.event_participants.components.previewParticipant
import com.avilanii.attend.ui.theme.AttenDTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantListScreen(
    state: ParticipantListState,
    modifier: Modifier = Modifier,
    onAction: (ParticipantListAction)->Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isEventActionMenuOpen by remember { mutableStateOf(false) }

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        if (result.contents != null)
            onAction(ParticipantListAction.OnScanQrClick(result.contents))
    }

    val exportToCsvLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        uri?.let {
            onAction(ParticipantListAction.OnExportToCSVClick(uri))
        }
    }

    val importFromCsvLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            onAction(ParticipantListAction.OnImportFromCSVClick(uri))
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Participants") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {onAction(ParticipantListAction.OnMenuIconClick)}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open event menu")
                    }
                },
                actions = {
                    Box {
                        IconButton(
                            onClick = { isEventActionMenuOpen = true }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Event action menu"
                            )
                        }
                        DropdownMenu(
                            expanded = isEventActionMenuOpen,
                            onDismissRequest = { isEventActionMenuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Generate invite QR") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    onAction(ParticipantListAction.OnGenerateInviteQrOpenDialog)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Scan attendee QR") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    scanLauncher.launch(ScanOptions().apply {
                                        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                                        setBeepEnabled(true)
                                        setOrientationLocked(false)
                                    })
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Modify tiers") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    onAction(ParticipantListAction.OnModifyEventTiersClick)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Import from CSV") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    importFromCsvLauncher.launch(arrayOf(
                                        "text/csv",
                                        "text/comma-separated-values",
                                        "application/csv",
                                        "text/plain"
                                    ))
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Export to CSV") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    exportToCsvLauncher.launch("Participants.csv")
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {onAction(ParticipantListAction.OnAddParticipantClick)},
                icon = { Icon(Icons.Filled.Add, "Add participant") },
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
            if (state.inviteQr != null){
                EventInviteQRDialog(
                    qrCode = state.inviteQr
                ) {
                    onAction(ParticipantListAction.OnGenerateInviteQrDismissDialog)
                }
            }
            if(state.isReviewingCheckIn && state.checkInResponse != null){
                CheckInReviewDialog(
                    checkInResponse = state.checkInResponse
                ) {
                    onAction(ParticipantListAction.OnDismissReviewCheckIn)
                }
            }
            if(state.isModifyingAttendeeTiers){
                AttendeeTiersDialog(
                    tiers = state.eventTiers,
                    isAssigningTier = state.isAssigningTier,
                    onDeleteTierClick = { tierId ->
                        onAction(ParticipantListAction.OnRemoveEventTierClick(tierId))
                    },
                    onAddTierClick = { tierTitle ->
                        onAction(ParticipantListAction.OnAddEventTierClick(tierTitle))
                    },
                    onAssignTierClick = { tierId ->
                        onAction(ParticipantListAction.OnAssignParticipantTierClick(
                            participantId = state.selectedParticipant!!.id,
                            tierId = tierId
                        ))
                    },
                    onResignTierClick = {
                        onAction(ParticipantListAction.OnResignParticipantTierClick(state.selectedParticipant!!.id))
                    }
                ) {
                    onAction(ParticipantListAction.OnDismissModifyEventTiersClick)
                }
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