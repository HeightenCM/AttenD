package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TapAndPlay
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.presentation.event_list.components.previewEvent
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun EventQrDialog(
    eventTitle: String,
    qrCode: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = eventTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = modifier.fillMaxWidth()
                )
                Text(
                    text = "You may scan the following qr:"
                )
                QrCodeDisplay(qrCode, modifier = modifier.fillMaxWidth().padding(10.dp))
                Text(
                    text = "Alternatively, you may tap your phone on the smart gate when NFC is on.",
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.TapAndPlay,
                    contentDescription = "NFC"
                )
            }

        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEventQrDialog() {
    AttenDTheme {
        EventQrDialog(eventTitle = previewEvent.name, qrCode = "testing") {

        }
    }
}