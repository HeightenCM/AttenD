package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.domain.SmartGate
import com.avilanii.attend.ui.theme.AttenDTheme
import com.avilanii.attend.ui.theme.greenBackground

@Composable
fun SmartGateListItem(
    modifier: Modifier = Modifier,
    smartGate: SmartGate,
    onClick: () -> Unit
) {
    val textValue = if (smartGate.isOnline)
        " ON "
    else
        " OFF "
    val boxColor = if (smartGate.isOnline)
        greenBackground
    else
        MaterialTheme.colorScheme.errorContainer
    val textColor = if (smartGate.isOnline)
        Color.Green
    else
        MaterialTheme.colorScheme.onErrorContainer
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Text(
            text = smartGate.name
        )
        Box (
            modifier = modifier
                .clip(RoundedCornerShape(30f))
                .background(boxColor)
                .padding()
        ) {
            Text(
                text = textValue,
                color = textColor)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSmartGateListItem() {
    AttenDTheme {
        SmartGateListItem(
            smartGate = SmartGate(15, "Aha", false)
        ) {

        }
    }
}