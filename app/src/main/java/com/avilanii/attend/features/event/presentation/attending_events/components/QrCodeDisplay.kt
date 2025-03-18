package com.avilanii.attend.features.event.presentation.attending_events.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.core.presentation.generateQrCode
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun QrCodeDisplay(qrContent: String, modifier: Modifier = Modifier) {
    val qrBitmap = remember(qrContent) { generateQrCode(qrContent) }
    qrBitmap?.let { bitmap ->
        Image(
            painter = BitmapPainter(bitmap.asImageBitmap()),
            contentDescription = "QR Code",
            modifier = modifier
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewQrCodeDisplay() {
    AttenDTheme {
        QrCodeDisplay("lol")
    }
}