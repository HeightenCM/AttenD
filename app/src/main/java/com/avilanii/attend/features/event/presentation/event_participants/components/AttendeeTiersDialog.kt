package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AttendeeTiersDialog(
    modifier: Modifier = Modifier,
    tiers: List<AttendeeTier>,
    onDeleteTierClick: (AttendeeTier) -> Unit,
    onAddTierClick: (AttendeeTier) -> Unit,
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
            LazyColumn {
                items(tiers) { tier ->
                    AttendeeTierListItem(
                        tierTitle = tier.title
                    ) {
                        onDeleteTierClick(tier)
                    }
                }
            }
            IconButton(
                onClick = TODO("Add option for user to add tiers"),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Add event tier"
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAttendeeTiersDialog() {
    AttenDTheme {
        AttendeeTiersDialog(
            tiers = listOf(AttendeeTier("Gold"), AttendeeTier("Silver")),
            onDeleteTierClick = {},
            onAddTierClick = {}
        ) {

        }
    }
}