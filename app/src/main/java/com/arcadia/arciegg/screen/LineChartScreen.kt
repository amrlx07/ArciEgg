package com.arcadia.arciegg.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadia.arciegg.uiState.SensorData
import com.arcadia.arciegg.viewModel.LineChartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ListDataScreen(viewModel: LineChartViewModel = viewModel()) {
    val sensorDetailData by viewModel.monitoringData.observeAsState(initial = emptyList())

    LazyColumn {
        items(sensorDetailData) { data ->
            ListDetailItem(sensorData = data)
        }
    }
}
@Composable
fun ListDetailItem(sensorData: SensorData){
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${sensorData.DateTime}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(15.dp))
            Text(text = "${sensorData.Kelembapan}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(15.dp))
            Text(text = "${sensorData.Suhu}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(15.dp))
            Text(text = "${sensorData.SetPointKelembapan}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(15.dp))
            Text(text = "${sensorData.SetPointSuhu}", modifier = Modifier.padding(5.dp))
        }
        HorizontalDivider(modifier = Modifier.width(225.dp))
    }
}

@Preview(showBackground = true
)
@Composable
fun ListDetailItemPreview() {
    val listExample = SensorData(67284423,76.5,35.4,76.5,35.4)
    ListDetailItem(listExample)
}