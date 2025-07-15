package com.avilanii.attend.features.event.presentation.event_analytics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventAnalyticsScreen(modifier: Modifier = Modifier,
                         state: EventAnalyticsScreenState,
                         onAction: (EventAnalyticsScreenAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics") },
                navigationIcon = {
                    IconButton(onClick = {onAction(EventAnalyticsScreenAction.OnMenuIconClick)}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open event menu")
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
            paddingValues
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