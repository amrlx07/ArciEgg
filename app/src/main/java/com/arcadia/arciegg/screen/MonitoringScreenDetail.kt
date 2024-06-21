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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcadia.arciegg.R

@Composable
fun MonitoringScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.orange50)),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        MonitoringCircular()
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider(modifier = Modifier.width(328.dp))
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Batas Nilai",
            style = TextStyle(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 15.dp))
        SetPoint()
    }
}

@Composable
fun MonitoringCircular() {
    ElevatedCard(
        modifier = Modifier.height(305.dp).width(328.dp),
        colors = CardDefaults.elevatedCardColors(colorResource(R.color.orange200))
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
                    indicatorValue = 30.0,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = Color(0xFFFFEDE8),
                    foregroundIndicatorColor = Color(0xFF83B4FF),
                    label = "Temperature"
                )
                CircularIndicator(
                    canvasSize = 200.dp,
                    sizeCircular = 1.5f,
                    indicatorValue = 70.0,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = Color(0xFFFEECE7),
                    foregroundIndicatorColor = Color(0xFFA9F2C6),
                    label = "Humidity"
                )
            }
            Row{
                MonitroingCard(
                    textIndicator = "Suhu",
                    R.drawable.temperature,
                    R.color.blue500,
                    "suhu",
                    30.0f ,
                    modifier = Modifier.width(146.dp).height(84.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                MonitroingCard(
                    textIndicator = "Kelembapan",
                    R.drawable.humidity,
                    R.color.green500,
                    "Kelembapan",
                    70.0f,
                    modifier = Modifier.width(146.dp).height(84.dp)
                )
            }
        }
    }
}

@Composable
fun MonitroingCard(
    textIndicator: String,
    @DrawableRes iconIndicator: Int,
    @ColorRes colorBackgroundIndicator: Int,
    contentDescription: String,
    valueIndicator: Float,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.elevatedCardColors(colorResource(colorBackgroundIndicator)),

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

            }
        }
    }
}

@Composable
fun SetPoint() {
    Column {
        SetPointCard(
            title = "Suhu",
            valueSetPoint = 30f,
            onSetPointValueChange = {},
            rangeSetPoint = 0f..100f,
            colorThumbIndicator = R.color.blue500
        )
        Spacer(modifier = Modifier.height(15.dp))
        SetPointCard(
            title = "Kelembapan",
            valueSetPoint = 50f,
            onSetPointValueChange = {},
            rangeSetPoint = 0f..100f,
            colorThumbIndicator = R.color.green500
        )
    }
}

@Composable
fun SetPointCard(
    title: String,
    valueSetPoint: Float,
    onSetPointValueChange: (Float) -> Unit,
    rangeSetPoint: ClosedFloatingPointRange<Float>,
    @ColorRes colorThumbIndicator: Int,
) {
    ElevatedCard(
        modifier = Modifier.width(328.dp).height(71.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.elevatedCardColors(colorResource(R.color.orange200))
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
                value = valueSetPoint,
                onValueChange = onSetPointValueChange,
                valueRange = rangeSetPoint,
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(colorThumbIndicator),
                    activeTrackColor = colorResource(colorThumbIndicator)
                ),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
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
        colorThumbIndicator = R.color.blue500)
}


@Preview(showSystemUi = true)
@Composable
fun MonitoringScreenPreview() {
    MonitoringScreen()
}

