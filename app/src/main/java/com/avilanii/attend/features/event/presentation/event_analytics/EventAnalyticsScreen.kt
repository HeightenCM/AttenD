package com.avilanii.attend.features.event.presentation.event_analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.features.event.presentation.event_analytics.components.EventPieChart
import com.avilanii.attend.ui.theme.AttenDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventAnalyticsScreen(modifier: Modifier = Modifier,
                         state: EventAnalyticsScreenState,
                         onAction: (EventAnalyticsScreenAction) -> Unit
) {
    var isEventActionMenuOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics") },
                navigationIcon = {
                    IconButton(onClick = {onAction(EventAnalyticsScreenAction.OnMenuIconClick)}) {
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
                                text = { Text("Tier distribution pie chart") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    onAction(EventAnalyticsScreenAction.OnTierDistributionPieClick)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Status distribution pie chart") },
                                onClick = {
                                    isEventActionMenuOpen = false
                                    onAction(EventAnalyticsScreenAction.OnParticipantStatusDistributionClick)
                                }
                            )
                        }
                    }
                }
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
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isShowingPie) {
                    EventPieChart(
                        modifier = Modifier
                            .padding(paddingValues),
                        data = state.pieValues,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewEventAnalyticsScreen() {
    AttenDTheme {
        EventAnalyticsScreen(
            state = EventAnalyticsScreenState(),
            onAction = {}
        )
    }
}