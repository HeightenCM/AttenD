package com.avilanii.attend.features.event.presentation.event_iot

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
import androidx.compose.material3.HorizontalDivider
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
import com.avilanii.attend.features.event.domain.SmartGate
import com.avilanii.attend.features.event.presentation.event_iot.components.ActivateGateDialog
import com.avilanii.attend.features.event.presentation.event_iot.components.AddSmartGateDialog
import com.avilanii.attend.features.event.presentation.event_iot.components.GateTierListDialog
import com.avilanii.attend.features.event.presentation.event_iot.components.SmartGateListItem
import com.avilanii.attend.ui.theme.AttenDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventIotScreen(
    modifier: Modifier = Modifier,
    state: EventIotScreenState,
    onAction: (EventIotScreenAction) -> Unit
    ) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IoT Devices") },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = {onAction(EventIotScreenAction.OnMenuIconClick)}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open event menu")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {onAction(EventIotScreenAction.OnAddGateClick)},
                icon = { Icon(Icons.Filled.Add, "Add IoT Device") },
                text = { Text("Add IoT device") },
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
                items(state.smartGates) { smartGate ->
                    SmartGateListItem(
                        smartGate = smartGate,
                    ) {
                        onAction(EventIotScreenAction.OnGateClick(smartGate))
                    }
                    HorizontalDivider()
                }
            }
            if(state.isAddingSmartGate){
                AddSmartGateDialog(
                    onSubmit = { name ->
                        onAction(EventIotScreenAction.OnCreateGateClick(name))
                    }
                ) {
                    onAction(EventIotScreenAction.OnDismissAddGateClick)
                }
            }
            if(state.isActivatingGate){
                ActivateGateDialog {
                    onAction(EventIotScreenAction.OnDismissActivateGateClick)
                }
            }
            if(state.isManagingGateTiers && state.selectedGateId != null && !state.tiers.isEmpty()){
                GateTierListDialog(
                    tiers = state.tiers,
                    onChangeTierStateClick = { tierId ->
                        onAction(EventIotScreenAction.OnChangeTierStateClick(state.selectedGateId, tierId))
                    }
                ) {
                    onAction(EventIotScreenAction.OnDismissGateTierDialog)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEventIotScreen() {
    AttenDTheme {
        EventIotScreen(
            state = EventIotScreenState(
                smartGates = listOf(
                    SmartGate(
                        name = "Hello boss Gate",
                        isOnline = true,
                        id = 1
                    ),
                    SmartGate(
                        name = "Testing Gate",
                        isOnline = false,
                        id = 2
                    )
                )
            )
        ) { }
    }
}