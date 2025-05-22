package com.avilanii.attend.features.event.presentation.event_participants.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun CheckInReviewDialog(
    modifier: Modifier = Modifier,
    checkInResponse: CheckInConfirmationDTO,
    onDismiss: () -> Unit
) {
    val messageColor =
        if (checkInResponse.accepted) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.error
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    checkInResponse.message,
                    color = messageColor,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCheckInReviewDialog() {
    AttenDTheme {
        CheckInReviewDialog(
            checkInResponse = CheckInConfirmationDTO("Some message", false)
        ) { }
    }
}