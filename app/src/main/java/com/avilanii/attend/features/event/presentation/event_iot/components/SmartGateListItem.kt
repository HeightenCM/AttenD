package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.SmartGate
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun SmartGateListItem(
    modifier: Modifier = Modifier,
    smartGate: SmartGate,
    onRemoveTierClick: (Pair<AttendeeTier, Int>) -> Unit,
    onAddTierClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Testare",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "TOTAL: " + smartGate.allowedTiers.sumOf { it.second },
            style = MaterialTheme.typography.bodySmall
        )
        smartGate.allowedTiers.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = it.first.title+": "+it.second.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                IconButton(
                    onClick = {
                        onRemoveTierClick(it)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Disallow tier"
                    )
                }
            }
        }
        IconButton(
            onClick = onAddTierClick
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Add tier"
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSmartGateListItem() {
    AttenDTheme {
        SmartGateListItem(
            smartGate = SmartGate(
                name = "Main Gate",
                allowedTiers = listOf(
                    Pair(AttendeeTier("Premium"), 5),
                    Pair(AttendeeTier("Gold"), 10)
                )
            ),
            onRemoveTierClick = {}
        ){

        }
    }
}