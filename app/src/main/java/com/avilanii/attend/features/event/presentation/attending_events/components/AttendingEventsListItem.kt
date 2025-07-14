package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.presentation.event_list.components.previewEvent
import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AttendingEventsListItem(
    modifier: Modifier = Modifier,
    eventUi: EventUi,
    onClick: () -> Unit
    ) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = eventUi.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Event date")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${eventUi.startDateTime.formatted} - ${eventUi.endDateTime.formatted}",
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Venue")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = eventUi.venue)
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Filled.Face, contentDescription = "Organizer")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = eventUi.organizer ?: "Unknown")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAttendingEventsListItem() {
    AttenDTheme {
        AttendingEventsListItem(
            eventUi = previewEvent,
            onClick = {  }
        )
    }
}