package com.avilanii.attend.features.event.presentation.event_iot.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun GateTierListDialog(
    modifier: Modifier = Modifier,
    tiers: List<AttendeeTier>,
    onSelectTierClick: (AttendeeTier) -> Unit,
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(tiers) { tier ->
                        Text(
                            text = tier.title,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable(
                                onClick = {
                                    onSelectTierClick(tier)
                                }
                            ),
                            textAlign = TextAlign.Center
                        )
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
                AttendeeTier("Gold"), AttendeeTier("Silver")
            ),
            onSelectTierClick = {

            }
        ){

        }
    }
}