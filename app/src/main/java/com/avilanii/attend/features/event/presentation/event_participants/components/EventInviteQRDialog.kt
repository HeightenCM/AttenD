package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.presentation.attending_events.components.QrCodeDisplay

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
                .fillMaxWidth()
                .height(300.dp)
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            QrCodeDisplay(qrCode, modifier = modifier.fillMaxWidth().padding(10.dp))
        }
    }
}