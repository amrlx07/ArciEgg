package com.arcadia.arciegg.screen

import android.graphics.PathEffect
import android.graphics.PointF
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arcadia.arciegg.uiState.SensorData
import com.arcadia.arciegg.viewModel.LineChartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Path
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toIntRect
import androidx.compose.ui.unit.toSize
import dev.riggaroo.composeplaytime.BarColor
import dev.riggaroo.composeplaytime.HighlightColor
import dev.riggaroo.composeplaytime.graphData
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineScreenMain() {
    Scaffold (
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Grafik Garis")
                }
            )
        }
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabList()
        }
    }
}

@Composable
fun TabList(viewModel: LineChartViewModel = viewModel()) {
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
            0 -> ListDataScreenSuhu (valueSelector = { it.Suhu }, setPointValueSelector = { it.SetPointSuhu })
            1 -> ListDataScreenKelembapan (valueSelector = { it.Kelembapan }, setPointValueSelector = { it.SetPointKelembapan })
        }
    }
}

@Composable
fun ListDataScreenSuhu(viewModel: LineChartViewModel = viewModel(), valueSelector: (SensorData) -> Double,setPointValueSelector :(SensorData) -> Double) {
    val sensorDetailData by viewModel.monitoringData.observeAsState(initial = emptyList())

    LazyColumn {
        item {
            LineHeader(title = "Suhu", simbolValue = "Celcius(°C)", modifier = Modifier.padding(10.dp).fillMaxWidth())
        }
        item {
            LineChart(data = sensorDetailData, valueSelector = valueSelector, setPointValueSelector = setPointValueSelector)
        }
        item{
            ListDetailItemHeader(mainValueTitle = "Suhu", setPointTitle = "Batas")
        }

        items(sensorDetailData) { data ->
            ListDetailItemSuhu(sensorData = data)
        }
    }
}
@Composable
fun ListDataScreenKelembapan(viewModel: LineChartViewModel = viewModel(), valueSelector: (SensorData) -> Double,setPointValueSelector :(SensorData) -> Double) {
    val sensorDetailData by viewModel.monitoringData.observeAsState(initial = emptyList())

    LazyColumn {
        item {
            LineHeader(title = "Kelembapan", simbolValue = "Persentase(%)", modifier = Modifier.padding(10.dp).fillMaxWidth())
        }
        item {
            LineChart(data = sensorDetailData, valueSelector = valueSelector, setPointValueSelector = setPointValueSelector)
        }
        item{
            ListDetailItemHeader(mainValueTitle = "Kelembapan", setPointTitle = "Batas")
        }

        items(sensorDetailData) { data ->
            ListDetailItemKelembapan(sensorData = data)
        }
    }
}
//@Preview
@Composable
fun LineHeader(
    modifier: Modifier = Modifier,
    title : String,
    simbolValue: String
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = simbolValue,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun ListDetailItemSuhu(sensorData: SensorData, viewModel: LineChartViewModel = viewModel()){
    val formattedDate = viewModel.formatDateTime(sensorData.DateTime)
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = formattedDate, modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = "${sensorData.Suhu}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = "${sensorData.SetPointSuhu}", modifier = Modifier.padding(5.dp))
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}
@Composable
fun ListDetailItemHeader(
    mainValueTitle: String,
    setPointTitle: String
){
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tanggal", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = mainValueTitle, modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = setPointTitle, modifier = Modifier.padding(5.dp))
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}
@Composable
fun ListDetailItemKelembapan(sensorData: SensorData, viewModel: LineChartViewModel = viewModel()){
    val formattedDate = viewModel.formatDateTime(sensorData.DateTime)
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = formattedDate, modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = "${sensorData.Kelembapan}", modifier = Modifier.padding(5.dp))
            VerticalDivider(modifier = Modifier.height(20.dp))
            Text(text = "${sensorData.SetPointKelembapan}", modifier = Modifier.padding(5.dp))
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun LineChart(data: List<SensorData>, valueSelector: (SensorData) -> Double, setPointValueSelector :(SensorData) -> Double) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()

    ) {
        val animationProgress = remember { Animatable(0f) }
        var highlightedWeek by remember { mutableStateOf<Int?>(null) }
        val localView = LocalView.current

        LaunchedEffect(highlightedWeek) {
            if (highlightedWeek != null) {
                localView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
        }

        LaunchedEffect(key1 = data, block = {
            animationProgress.animateTo(1f, tween(3000))
        })

        val coroutineScope = rememberCoroutineScope()
        val textMeasurer = rememberTextMeasurer()
        val labelTextStyle = MaterialTheme.typography.labelSmall

        Spacer(modifier = Modifier
            .padding(8.dp)
            .aspectRatio(3 / 2f)
            .fillMaxSize()
            .align(Alignment.Center)
            .pointerInput(Unit) {
                detectTapGestures {
                    coroutineScope.launch {
                        animationProgress.snapTo(0f)
                        animationProgress.animateTo(1f, tween(3000))
                    }
                }
            }
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDragStart = {offset ->
                        highlightedWeek =
                            (offset.x / (size.width / (data.size -1 ))).roundToInt()
                    },
                    onDragEnd = {highlightedWeek = null},
                    onDragCancel = {highlightedWeek = null},
                    onDrag = {change , _ ->
                        highlightedWeek =
                            (change.position.x / (size.width / (data.size -1 ))).roundToInt()
                    }
                )
            }
            .drawWithCache {
                val mainPath = generateSmoothPathSuhu(data, valueSelector, size)
                val setpoinPath = generateSmoothPathSuhu(data, setPointValueSelector, size)
                val filledPath = Path()
                filledPath.addPath(mainPath)
                filledPath.relativeLineTo(0f, size.height)
                filledPath.lineTo(0f, size.height)
                filledPath.close()

                onDrawBehind {
                    val barWidthPx = 1.dp.toPx()
                    drawRect(BarColor, style = Stroke(barWidthPx))

                    val verticalLines = 4
                    val verticalSize = size.width / (verticalLines + 1)
                    repeat(verticalLines) { i ->
                        val startX = verticalSize * (i + 1)
                        drawLine(
                            BarColor,
                            start = Offset(startX, 0f),
                            end = Offset(startX, size.height),
                            strokeWidth = barWidthPx
                        )
                    }
                    val horizontalLines = 3
                    val sectionSize = size.height / (horizontalLines + 1)
                    repeat(horizontalLines) { i ->
                        val startY = sectionSize * (i + 1)
                        drawLine(
                            BarColor,
                            start = Offset(0f, startY),
                            end = Offset(size.width, startY),
                            strokeWidth = barWidthPx
                        )
                    }

                    // draw line
                    clipRect(right = size.width * animationProgress.value) {
                        drawPath(mainPath, Color.Green, style = Stroke(2.dp.toPx()))
                        drawPath(
                            setpoinPath,
                            Color.Red.copy(alpha = 0.8f),
                            style = Stroke(2.dp.toPx())
                        )
                        drawPath(
                            filledPath,
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.Green.copy(alpha = 0.4f),
                                    Color.Transparent
                                )
                            ),
                            style = Fill
                        )
                    }
                    // draw highlight if user is dragging
                    highlightedWeek?.let {week ->
                        if (week in data.indices){
                            this.drawHighlight(
                                highlightedWeek = week,
                                graphData = data,
                                valueSelector = valueSelector,
                                textMeasurer = textMeasurer,
                                labelTextStyle = labelTextStyle
                            )
                        }

                    }
                }
            })
    }
}// Fungsi untuk menggambar grafik

fun generateSmoothPathSuhu(data: List<SensorData>,valueSelector: (SensorData) -> Double, size: Size): Path {
    val path = Path()

    if (data.isEmpty()) return path

    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxByOrNull (valueSelector)
        ?: return path
    val min = data.minByOrNull (valueSelector)
        ?: return path // will map to x= 0, y = height
    val range = valueSelector(max) - valueSelector(min)
    val heightPxPerAmount = size.height / range.toFloat()

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, sensorData ->
        val value = valueSelector(sensorData)
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (value - valueSelector(min)).toFloat() *
                        heightPxPerAmount
            )

        }

        val balanceX = i * weekWidth
        val balanceY = size.height - (value - valueSelector(min)).toFloat() *
                heightPxPerAmount
        // to do smooth curve graph - we use cubicTo, uncomment section below for non-curve
        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
        path.cubicTo(
            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX, balanceY
        )

        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}



@OptIn(ExperimentalTextApi::class)
fun DrawScope.drawHighlight(
    highlightedWeek: Int,
    graphData: List<SensorData>,
    valueSelector: (SensorData) -> Double,
    textMeasurer: TextMeasurer,
    labelTextStyle: TextStyle
) {
    val amount = valueSelector(graphData[highlightedWeek])
    val minAmount = graphData.minBy { valueSelector(it) }.let(valueSelector)
    val range = graphData.maxBy { valueSelector(it) }.let(valueSelector) - minAmount
    val percentageHeight = ((amount - minAmount).toFloat() / range.toFloat())
    val pointY = size.height - (size.height * percentageHeight)
    //draw vertical line on week
    val x = highlightedWeek * (size.width / (graphData.size - 1))

    drawLine(
        HighlightColor,
        start = Offset(x, 0f),
        end = Offset(x, size.height),
        strokeWidth = 2.dp.toPx(),
        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f,10f))
    )

    drawCircle(
        color = Color.Green,
        radius = 4.dp.toPx(),
        center = Offset(x, pointY)
    )

    val textLayoutResult = textMeasurer.measure("$amount", style = labelTextStyle)
    val highlightContainerSize = (textLayoutResult.size).toIntRect().inflate(4.dp.roundToPx()).size
    val boxTopLeft = (x - (highlightContainerSize.width / 2f))
        .coerceIn(0f, size.width - highlightContainerSize.width)
    drawRoundRect(
        color = Color.White,
        topLeft = Offset(boxTopLeft, 0f),
        size = highlightContainerSize.toSize(),
        cornerRadius = CornerRadius(4.dp.toPx())
    )
    drawText(
        textLayoutResult,
        color = Color.Black,
        topLeft = Offset(boxTopLeft + 4.dp.toPx(), 4.dp.toPx())
    )
}







