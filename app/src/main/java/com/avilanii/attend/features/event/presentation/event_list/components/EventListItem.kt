package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.domain.Event
import com.avilanii.attend.features.event.presentation.models.EventUi
import com.avilanii.attend.features.event.presentation.models.toEventUi
import com.avilanii.attend.ui.theme.AttenDTheme
import java.time.LocalDateTime

@Composable
fun EventListItem(eventUi: EventUi,
                  onClick: () -> Unit,
                  modifier: Modifier = Modifier) {
    val contentColor = if(isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    Row (modifier = modifier
        .clickable { onClick() }
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Text(
            text = eventUi.name,
            color = contentColor)
        EventState(
            date = eventUi.dateTime.value
        )
    }
}

@PreviewLightDark
@Composable
private fun EventListItemPreview() {
    AttenDTheme {
        EventListItem(
            eventUi = previewEvent,
            onClick = {})
    }
}

internal val previewEvent = Event(
    id = 5,
    name = "That damn exam",
    dateTime = LocalDateTime.now().minusDays(1),
    budget = 500
).toEventUi()