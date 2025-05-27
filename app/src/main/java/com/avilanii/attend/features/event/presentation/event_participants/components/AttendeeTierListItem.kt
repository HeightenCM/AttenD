package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AttendeeTierListItem(
    modifier: Modifier = Modifier,
    tierTitle: String,
    onDeleteClick: ()-> Unit
    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().padding(10.dp, 0.dp)
    ) {
        Text(tierTitle)
        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete attendee tier"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAttendeeListTierItem() {
    AttenDTheme {
        AttendeeTierListItem(
            tierTitle = "Testing"
        ){

        }
    }
}