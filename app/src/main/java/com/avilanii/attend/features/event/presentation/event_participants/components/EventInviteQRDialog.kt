package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.presentation.attending_events.components.QrCodeDisplay
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun EventInviteQRDialog(
    modifier: Modifier = Modifier,
    qrCode: String,
    onDismiss: () -> Unit
    ) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            QrCodeDisplay(qrCode, modifier = modifier
                .fillMaxSize())
        }
    }
}

@Preview
@Composable
private fun PreviewEventInviteQrDialog() {
    AttenDTheme {
        EventInviteQRDialog(
            qrCode = "idk man"
        ) { }
    }
}