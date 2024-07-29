@file:OptIn(ExperimentalMaterial3Api::class)

package com.arcadia.arciegg.screen

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arcadia.arciegg.R
import com.arcadia.arciegg.uiState.SensorCheckUiState
import com.arcadia.arciegg.viewModel.DetailMonitoringViewModel
import com.arcadia.arciegg.viewModel.LineChartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(
    dashboardScreen: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        "Monitoring",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = dashboardScreen) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to Dashboard" // Provide a descriptive content description
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Sensor Cek") }, // More descriptive text
                icon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = "Show Sensor Check") },
                onClick = { showBottomSheet = true }
            )
        }
    ) { innerPadding -> // Use innerPadding for content padding
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ... Your other content (MonitoringCircular, SetPoint, etc.)
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Indikator",
                    style = TextStyle(
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 15.dp))
                Spacer(modifier = Modifier.height(15.dp))
                MonitoringCircular()
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(modifier = Modifier.width(328.dp))
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = "Batas Nilai",
                    style = TextStyle(
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 15.dp))
                SetPoint()
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                // Sheet content with padding
                Column(Modifier.padding(16.dp)) {
                    Text("Sensor Cek", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    TabListSensorCheck()
                    // Add a close button
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { showBottomSheet = false }
                    }) {
                        Text("Tutup")
                    }
                }
            }
        }
    }
}

@Composable
fun MonitoringCircular(
    viewModel: DetailMonitoringViewModel = viewModel()
) {
    val state by viewModel.stateMonitor.collectAsStateWithLifecycle()
    val temperature = state?.Suhu
    val humidity = state?.Kelembapan

    ElevatedCard(
        modifier = Modifier.height(305.dp).width(328.dp),
        //colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box{
                CircularIndicator(
                    canvasSize = 200.dp,
                    sizeCircular = 1f,
                    indicatorValue = temperature,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                    foregroundIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
                    label = "Temperature"
                )
                CircularIndicator(
                    canvasSize = 200.dp,
                    sizeCircular = 1.5f,
                    indicatorValue = humidity,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                    foregroundIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    label = "Humidity"
                )
            }
            Row{
                MonitoringCard(
                    textIndicator = "Suhu",
                    R.drawable.temperature,
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    "suhu",
                    viewModel.roundup(temperature) ,
                    modifier = Modifier.width(146.dp).height(84.dp),
                    symbol = "°C"
                )
                Spacer(modifier = Modifier.width(20.dp))
                MonitoringCard(
                    textIndicator = "Kelembapan",
                    R.drawable.humidity,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    "Kelembapan",
                    viewModel.roundup(humidity),
                    modifier = Modifier.width(146.dp).height(84.dp),
                    symbol = "%"
                )
            }
        }
    }
}

@Composable
fun MonitoringCard(
    textIndicator: String,
    @DrawableRes iconIndicator: Int,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentDescription: String,
    valueIndicator: Double?,
    modifier: Modifier = Modifier,
    symbol: String
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.elevatedCardColors(containerColor),

        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = textIndicator,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 3.dp)
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(iconIndicator),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .width(20.dp)
                        .height(24.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = "$valueIndicator",
                    style = TextStyle(
                        fontSize = 28.sp
                    )
                )
                Text(
                    text = symbol,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        }
    }
}

@Composable
fun SetPoint(
    viewModel: DetailMonitoringViewModel = viewModel()
) {
    val state by viewModel.stateSetPoint.collectAsStateWithLifecycle()
    val temperature = state?.Suhu
    val humidity = state?.Kelembapan

    var tempValue = temperature?.toFloat()
    var humValue = humidity?.toFloat()
    //var floatHumidityValue by remember { mutableStateOf(humidity?.toFloat() ?: 0f) }

    var floatTempValue by remember { mutableStateOf(0f) }
    var floatHumidityValue by remember { mutableStateOf( 0f) }

    Column {
        SetPointCard(
            title = "Suhu",
            valueSetPoint = tempValue,
            onSetPointValueChange = { newTempValue ->
                if (tempValue != null) {
                    floatTempValue = tempValue
                }
            },
            rangeSetPoint = 0f..100f,
            colorThumbIndicator = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.height(15.dp))
        SetPointCard(
            title = "Kelembapan",
            valueSetPoint = humValue,
            onSetPointValueChange = {newHumidityValue ->
                if (humValue != null) {
                    floatHumidityValue = humValue
                }
            },
            rangeSetPoint = 0f..100f,
            colorThumbIndicator = MaterialTheme.colorScheme.secondaryContainer
        )
    }
}

@Composable
fun SetPointCard(
    title: String,
    valueSetPoint: Float?,
    onSetPointValueChange: (Float) -> Unit,
    rangeSetPoint: ClosedFloatingPointRange<Float>,
    colorThumbIndicator: Color = MaterialTheme.colorScheme.primary,
) {
    ElevatedCard(
        modifier = Modifier.width(328.dp).height(71.dp),
        shape = RoundedCornerShape(25.dp),
        //colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$valueSetPoint",
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.alpha(70f/100f)
                )
            }
            Slider(
                value = valueSetPoint!!,
                onValueChange = onSetPointValueChange,
                valueRange = rangeSetPoint,
                colors = SliderDefaults.colors(
                    thumbColor = colorThumbIndicator,
                    activeTrackColor = colorThumbIndicator
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun TabListSensorCheck(viewModel: LineChartViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Suhu", "Kelembapan")
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed {index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index},
                    text = { Text(title) }
                )
            }
        }
        when(selectedTabIndex) {
            0 -> SensorGridSuhu()
            1 -> SensorGridKelembapan()
        }
    }
}
@Composable
fun SensorGridKelembapan(viewModel: DetailMonitoringViewModel = viewModel()) {
    val sensorCheck by viewModel.sensorCheckUiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            SensorCard(
                textIndicator = "Sensor 1",
                valueIndicator = sensorCheck.KelembapanCheck1,
                symbol = "%"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 2",
                valueIndicator = sensorCheck.KelembapanCheck2,
                symbol = "%"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 3",
                valueIndicator = sensorCheck.KelembapanCheck3,
                symbol = "%"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 4",
                valueIndicator = sensorCheck.KelembapanCheck4,
                symbol = "%"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 5",
                valueIndicator = sensorCheck.KelembapanCheck5,
                symbol = "%"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 6",
                valueIndicator =sensorCheck.KelembapanCheck6,
                symbol = "%"
            )
        }
    }
}
@Composable
fun SensorGridSuhu(viewModel: DetailMonitoringViewModel = viewModel()) {
    val sensorCheck by viewModel.sensorCheckUiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            SensorCard(
                textIndicator = "Sensor 1",
                valueIndicator = sensorCheck.SuhuCheck1,
                symbol = "°C"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 2",
                valueIndicator = sensorCheck.SuhuCheck2,
                symbol = "°C"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 3",
                valueIndicator = sensorCheck.SuhuCheck3,
                symbol = "°C"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 4",
                valueIndicator = sensorCheck.SuhuCheck4,
                symbol = "°C"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 5",
                valueIndicator = sensorCheck.SuhuCheck5,
                symbol = "°C"
            )
        }
        item {
            SensorCard(
                textIndicator = "Sensor 6",
                valueIndicator =sensorCheck.SuhuCheck6,
                symbol = "°C"
            )
        }
    }
}

@Composable
fun SensorCard(
    textIndicator: String,
    valueIndicator: Double?,
    viewmodel: DetailMonitoringViewModel = viewModel(),
    symbol: String
) {
    ElevatedCard(
        modifier = Modifier.width(60.dp).height(80.dp),
        shape = RoundedCornerShape(15.dp),
        ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = textIndicator,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 3.dp)
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (valueIndicator == 0.0) {
                    Text("Error: No value", color = Color.Red) // Indicate error for this sensor
                } else {
                    Text(text = "${viewmodel.roundup(valueIndicator)} " + symbol  , style = MaterialTheme.typography.bodyLarge)
                }
                //Text(
                   // text = "$valueIndicator",
                    //style = TextStyle(
                     //  fontSize = 28.sp
                    //)
                //)
            }
        }
    }
}

@Preview
@Composable
fun SetPointCardPreview() {
    SetPointCard(
        title = "Set Point",
        valueSetPoint = 50f, {},
        rangeSetPoint = 0f..100f,
        //colorThumbIndicator = R.color.blue500
    )
}


@Preview(showSystemUi = true)
@Composable
fun MonitoringScreenPreview() {
    //MonitoringScreen()
}

