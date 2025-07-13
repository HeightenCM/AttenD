package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AttendeeTiersDialog(
    modifier: Modifier = Modifier,
    tiers: List<AttendeeTier>,
    isAssigningTier: Boolean = false,
    onDeleteTierClick: (AttendeeTier) -> Unit,
    onAddTierClick: (AttendeeTier) -> Unit,
    onAssignTierClick: (AttendeeTier) -> Unit,
    onResignTierClick: () -> Unit,
    onDismiss: () -> Unit
) {
    var isAddingTier by rememberSaveable { mutableStateOf(false) }
    var newTier by rememberSaveable { mutableStateOf("") }

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
                        AttendeeTierListItem(
                            tierTitle = tier.title,
                            onAssignClick = {
                                if (isAssigningTier){
                                    onAssignTierClick(tier)
                                    onDismiss()
                                }
                            }
                        ) {
                            onDeleteTierClick(tier)
                        }
                        HorizontalDivider(
                            color = Color.LightGray
                        )
                    }
                }
                if (isAddingTier) {
                    OutlinedTextField(
                        value = newTier,
                        onValueChange = { newValue ->
                            newTier = newValue
                        },
                        label = {
                            Text("New tier (eg: Premium)")
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = if (isAddingTier || isAssigningTier)
                        Arrangement.SpaceBetween
                    else Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            isAddingTier = !isAddingTier
                        }
                    ) {
                        Icon(
                            imageVector = if (isAddingTier)
                                Icons.Filled.Clear
                            else Icons.Filled.AddCircle,
                            contentDescription = if(isAddingTier)
                                "Cancel"
                            else "Add event tier"
                        )
                    }
                    if (isAssigningTier){
                        IconButton(
                            onClick = {
                                onResignTierClick()
                                onDismiss()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Resign tier",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    if (isAddingTier) {
                        IconButton(
                            onClick = {
                                isAddingTier = false
                                onAddTierClick(AttendeeTier(title = newTier))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Confirm"
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAttendeeTiersDialog() {
    AttenDTheme {
        AttendeeTiersDialog(
            tiers = listOf(AttendeeTier(1, "Gold"), AttendeeTier(2, "Silver")),
            isAssigningTier = true,
            onDeleteTierClick = {},
            onAddTierClick = {},
            onAssignTierClick = {},
            onResignTierClick = {}
        ) {

        }
    }
}