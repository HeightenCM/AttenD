package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun ActivateGateDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
    ) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card (
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "You may now approach your device to the smart gate in order to activate it.\n" +
                            "Make sure NFC is turned on.",
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.Nfc,
                    contentDescription = "Activate smart gate via NFC"
                )
            }

        }
    }
}

@PreviewLightDark
@Composable
fun PreviewActivateGateDialog() {
    AttenDTheme {
        ActivateGateDialog {

        }
    }
}