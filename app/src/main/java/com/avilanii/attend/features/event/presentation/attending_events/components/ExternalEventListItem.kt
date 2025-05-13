package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.domain.ExternalQR
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun ExternalEventListItem(
    modifier: Modifier = Modifier,
    externalQR: ExternalQR,
    onClick: (ExternalQR) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        onClick = { onClick(externalQR) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(externalQR.title)
            Text("External event", color = MaterialTheme.colorScheme.onErrorContainer)
        }

    }
}

@PreviewLightDark
@Composable
private fun PreviewExternalEventListItem() {
    AttenDTheme {
        ExternalEventListItem(
            modifier = Modifier,
            externalQR = ExternalQR(
                value = "idk man",
                title = "Still dunno"
            ),
            onClick = {}
        )
    }
}