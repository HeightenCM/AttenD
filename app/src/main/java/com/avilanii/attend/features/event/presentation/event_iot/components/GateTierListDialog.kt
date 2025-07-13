package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun GateTierListDialog(
    modifier: Modifier = Modifier,
    tiers: List<AttendeeTier>,
    onChangeTierStateClick: (AttendeeTier) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(tiers) { tier ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = tier.title
                                + ": " + tier.count
                            )
                            IconButton(
                                onClick = {
                                    onChangeTierStateClick(tier)
                                }
                            ) {
                                Icon(
                                    imageVector = if (tier.isAllowed ?: false)
                                        Icons.Filled.Check
                                    else
                                        Icons.Filled.Close,
                                    tint = if (tier.isAllowed ?: false)
                                        Color.Green
                                    else
                                        Color.Red,
                                    contentDescription = if (tier.isAllowed ?: false)
                                    "Disallow"
                                    else
                                    "Allow"
                                )
                            }
                        }
                        HorizontalDivider(
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGateTierListDialog() {
    AttenDTheme {
        GateTierListDialog(
            tiers = listOf(
                AttendeeTier("Gold", 1, true),
                AttendeeTier("Silver", 5, false)
            ),
            onChangeTierStateClick = {}
        ){

        }
    }
}