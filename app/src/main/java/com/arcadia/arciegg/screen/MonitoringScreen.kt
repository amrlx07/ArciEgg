package com.arcadia.arciegg.screen



import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcadia.arciegg.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

@Composable
fun MonitoringScreen() {
//    val gradient = Brush.verticalGradient(
//        colors = listOf(
//            colorResource(id = R.color.orange100),
//            colorResource(id = R.color.orange500)
//        )
//    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.orange50))
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().height(380.dp),
                colors = CardDefaults.elevatedCardColors(colorResource(R.color.orange200)),
                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 68.dp, bottomEnd = 0.dp)
            ) { }
            Image(
                painter = painterResource(R.drawable.inkubator_telur_bebek_2024_jun_01_06_03_54pm_000_customizedview158771619164),
                contentDescription = "background image",
                modifier = Modifier.offset(x = 180.dp, y = 115.dp)
            )

            Column {
                HeaderDashboard()
                Spacer(modifier = Modifier.height(24.dp))
                ProfileDasboard()
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Monitoring",
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(end = 10.dp)
            )
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.height(15.dp))
        MiniDashboard()
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Tentang Kontrol Otomatis",
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(end = 10.dp)
            )
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyAboutControl()
    }
}

@Composable
fun HeaderDashboard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.asset_5),
            contentDescription = "icon Arci Egg",
            modifier = Modifier
                .width(33.dp)
                .height(39.dp)
        )
        Text(text = "ARCI EGG",
            style = TextStyle(
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.width(120.dp))
        IconButton(
            onClick = {/*TODO*/},
            modifier = Modifier.width(50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                Text(text = "keluar")
            }
        }
        Spacer(modifier = Modifier.width(26.dp))
        IconButton(
            onClick = {/*TODO*/},
            modifier = Modifier
                .width(50.dp)
                .padding()
        ) {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notification"
            )
        }
    }
}

@Composable
fun ProfileDasboard() {
    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Text(
            text = "Selamat Datang",
            style = TextStyle(
                fontSize = 24.sp
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Akun",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "userid102597",
                style = TextStyle(
                    fontSize = 16.sp
                ))
        }
    }
}

@Composable
fun MiniDashboard() {
    ElevatedCard(
        modifier = Modifier
            .width(328.dp)
            .height(185.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.elevatedCardColors(colorResource(R.color.orange200))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(15.dp)
            ) {
                CardIndicator(
                    textIndicator = "Suhu",
                    R.drawable.temperature,
                    R.color.blue500,
                    "suhu",
                    30.0f ,
                    modifier = Modifier
                        .width(17.dp)
                        .height(33.dp)
                        .padding(end = 4.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                CardIndicator(
                    textIndicator = "Kelembapan",
                    R.drawable.humidity,
                    R.color.green500,
                    "Kelembapan",
                    70.0f,
                    modifier = Modifier
                        .width(20.dp)
                        .height(24.dp)
                        .padding(end = 4.dp)
                )
            }
            Box{
                CircularIndicator(
                    sizeCircular = 1.25f,
                    indicatorValue = 30.0,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = Color(0xFFFFEDE8),
                    foregroundIndicatorColor = Color(0xFF83B4FF),
                    label = "Temperature"
                    )
                CircularIndicator(
                    sizeCircular = 2f,
                    indicatorValue = 70.0,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = Color(0xFFFEECE7),
                    foregroundIndicatorColor = Color(0xFFA9F2C6),
                    label = "Humidity"
                )
            }
        }
    }
}


@Composable
fun CardIndicator(
    textIndicator: String,
    @DrawableRes iconIndicator: Int,
    @ColorRes colorBackgroundIndicator: Int,
    contentDescription: String,
    valueIndicator: Float,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = Modifier
            .width(126.dp)
            .height(70.dp),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.elevatedCardColors(colorResource(colorBackgroundIndicator)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 21.dp
        )
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
                    modifier = modifier
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
fun CircularIndicator(
    sizeCircular: Float,
    canvasSize: Dp = 250.dp,
    indicatorValue: Double?, //  nilai yang akan di tampilkan pada indicator
    maxIndicatorValue: Int , // nilai maksimal pada indicator
    backgroundIndicatorColor: Color,
    backgroundIndicatorStrokeWidth: Float = 50f,
    foregroundIndicatorColor: Color,
    foregroundIndicatorStrokeWidth: Float = 50f,
    label : String
) {
    // variable yang menampung nilai indicator yang di izinkan
    var allowedIndicatorValue by remember {
        mutableIntStateOf(maxIndicatorValue)
    }
    if (indicatorValue != null) {
        //  nilai indicator tidak boleh lebih dari nilai maksimal
        allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
            indicatorValue.toInt()
        } else {
            maxIndicatorValue
        }
    }
    // variable yang berfungsi animasi dari nilai indicator yang akan diubah menjadi type data float
    //berdasarkan nilai yang diizinkan
    var animatedIndicatorValue by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue =allowedIndicatorValue.toFloat()
    }
    // variable yang mengubah nilai menjadi persentase
    val percentage = (animatedIndicatorValue / maxIndicatorValue) * 100
    // variable untuk melakukan animasi Sweep angle berdasarkan Nilai percentage
    val sweepAngleAnimation by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000),
        label = label
    )
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .offset(y = 10.dp, x = 10.dp)
            .size(canvasSize)
            .drawBehind {
                val componentSize = size /sizeCircular
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    sweepAngle = sweepAngleAnimation,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
                )
            }
    ) {

    }
}

//latar Belakang dari Circular Indikator
fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x =(size.width - componentSize.width)/2f,
            y =(size.height - componentSize.height)/2f
        )
    )
}

//pengisi dari background indikator dengan warna yang ditentukan
fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x =(size.width - componentSize.width)/2f,
            y =(size.height - componentSize.height)/2f
        )
    )
}

@Composable
fun LazyAboutControl() {

}

@Composable
fun AboutControlCard() {

}

@Preview(showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440")
@Composable
fun GreetingPreview() {
    MonitoringScreen()
}