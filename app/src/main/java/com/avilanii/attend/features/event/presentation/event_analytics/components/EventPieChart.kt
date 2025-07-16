package com.avilanii.attend.features.event.presentation.event_analytics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.ui.theme.AttenDTheme
import com.jaikeerthick.composable_graphs.composables.pie.PieChart
import com.jaikeerthick.composable_graphs.composables.pie.model.PieData

@Composable
fun EventPieChart(
    data: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val pieData = remember(data) { data.toPieChartData() }
    var selectedSlice by remember { mutableStateOf<PieData?>(null) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        PieChart(
            modifier = Modifier.fillMaxSize(),
            data = pieData,
            onSliceClick = { slice ->
                selectedSlice = slice
            }
        )
        selectedSlice?.let { slice ->
            Text(
                text = "${slice.label}: ${slice.value.toInt()}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEventPieChart() {
    AttenDTheme {
        EventPieChart(
            data = listOf(
                "Apples" to 30,
                "Bananas" to 20,
                "Cherries" to 50
            )
        )
    }
}

fun List<Pair<String,Int>>.toPieChartData(): List<PieData>{
    val palette = listOf(
        Color(0xFF4CAF50), Color(0xFF009688),
        Color(0xFFFFC107), Color(0xFF03A9F4),
        Color(0xFFE91E63)
    )
    val total = this.sumOf { it.second.toDouble() }
    return this.mapIndexed { i, (label, value) ->
        PieData(
            value = value.toFloat(),
            label = label,
            color = palette.getOrElse(i) { Color.Gray }
        )
    }
}