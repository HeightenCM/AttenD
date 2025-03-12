package com.avilanii.attend.core.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.avilanii.attend.core.presentation.ObserveAsEvents
import com.avilanii.attend.core.presentation.toString
import com.avilanii.attend.features.event.presentation.event_list.EventListEvent
import com.avilanii.attend.features.event.presentation.event_list.EventListScreen
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApplicationRootComposable(
    modifier: Modifier = Modifier,
    viewModel: EventListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when (event) {
            is EventListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    EventListScreen(
        modifier = modifier,
        state = state
    ) { action ->
        viewModel.onAction(action)
    }
}