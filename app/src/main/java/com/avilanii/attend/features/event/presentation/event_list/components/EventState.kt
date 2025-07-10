package com.avilanii.attend.features.event.presentation.event_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.avilanii.attend.ui.theme.AttenDTheme
import com.avilanii.attend.ui.theme.greenBackground
import java.time.LocalDateTime

@Composable
fun EventState(
    modifier: Modifier = Modifier,
    startDate: LocalDateTime,
    endDate: LocalDateTime) {
    val textValue = if(startDate > LocalDateTime.now()){
        " COMING "
    } else if(endDate < LocalDateTime.now()){
        " ENDED "
    } else {
        " ONGOING "
    }
    val boxColor = if(startDate>LocalDateTime.now()){
        greenBackground
    } else if(endDate < LocalDateTime.now()){
        MaterialTheme.colorScheme.errorContainer
    } else {
        Color.Blue
    }
    val textColor = if(startDate>LocalDateTime.now()){
        Color.Green
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
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

@PreviewLightDark
@Composable
private fun EventStatePreview() {
    AttenDTheme {
        EventState(startDate = LocalDateTime.parse("2025-07-09T03:00:25.952929"),
            endDate = LocalDateTime.parse("2026-03-13T03:00:25.952929"))
    }
}