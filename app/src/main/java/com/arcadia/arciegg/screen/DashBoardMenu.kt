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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.arcadia.arciegg.uiState.UserData
import com.arcadia.arciegg.viewModel.DashBoardMonitoringViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardMenu(
    onSignOut: () -> Unit,
    userData: UserData?,
    detailScreen: () -> Unit,
    //onNotication: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(text = "Arci Egg",
                        fontSize = 30.sp)
                },
                actions = {
                    HeaderDashboard(
                        onSignOut = onSignOut,
                        //onNotication = onNotication
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp) // Add spacing between items
                ) {
                    item {
                        Box {
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp), // Let the card adjust its height
                                ///colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 68.dp, bottomEnd = 0.dp)
                            ) {  }

                            Image(
                                painter = painterResource(R.drawable.inkubator_telur_bebek_2024_jun_01_06_03_54pm_000_customizedview158771619164),
                                contentDescription = "background image",
                                modifier = Modifier
                                    .offset(x = 200.dp, y = 70.dp)
                                    .scale(1f)
                            )

                            Column {
                                Spacer(modifier = Modifier.height(24.dp))
                                val userName = userData?.userName ?: "Pengguna"
                                ProfileDasboard(userName = userName, userData = userData)
                            }
                        }
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 18.dp)
                        ) {
                            Text(
                                text = "Monitoring",
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(end = 10.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            HorizontalDivider()
                        }
                    }

                    item {
                        MiniDashboard(detailScreen = detailScreen)
                    }

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 18.dp)
                        ) {
                            Text(
                                text = "Tentang Kontrol Otomatis",
                                style = TextStyle(fontSize = 16.sp),
                                modifier = Modifier.padding(end = 10.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            HorizontalDivider()
                        }
                    }

                    item {
                        LazyAboutControl()
                    }
                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                    item {
                        HorizontalDivider()
                    }

                    // Add more items as needed
                }
            }
        }
    )
}

@Composable
fun HeaderDashboard(
    onSignOut: () -> Unit,
    //onNotication: () -> Unit
) {
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
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.width(170.dp))
        IconButton(
            onClick = onSignOut,
            modifier = Modifier.width(50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                Text(text = "keluar",
                    style = TextStyle(
                        fontSize = 12.sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(modifier = Modifier.width(26.dp))
        /*
        IconButton(
            onClick = onNotication,
            modifier = Modifier
                .width(50.dp)
                .padding()
        ) {
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notification"
            )
        }*/
    }
}

@Composable
fun ProfileDasboard(userName: String, userData: UserData? ) {
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 50.dp)
    ) {
        Text(
            text = "Selamat Datang",
            style = TextStyle(
                fontSize = 24.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(userData?.profilPictureUrl != null) {
                AsyncImage(
                    model = userData.profilPictureUrl,
                    contentDescription = "Akun",
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(50.dp)
                        .height(50.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Akun",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = userName,
                style = TextStyle(
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun MiniDashboard(
    viewModel: DashBoardMonitoringViewModel = viewModel(),
    detailScreen: () -> Unit,

) {
    val state by viewModel.stateMonitor.collectAsStateWithLifecycle()
    val temperature = state?.Suhu
    val humidity = state?.Kelembapan

    ElevatedCard(
        onClick = detailScreen,
        modifier = Modifier
            .width(328.dp)
            .height(185.dp),
        shape = RoundedCornerShape(36.dp),
        //colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentDescription = "suhu",
                    valueIndicator = viewModel.roundup(temperature) ,
                    symbol = "°C",
                    modifier = Modifier
                        .width(17.dp)
                        .height(33.dp)
                        .padding(end = 4.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                CardIndicator(
                    textIndicator = "Kelembapan",
                    R.drawable.humidity,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentDescription = "Kelembapan",
                    valueIndicator = viewModel.roundup(humidity),
                    symbol = "%",
                    modifier = Modifier
                        .width(20.dp)
                        .height(24.dp)
                        .padding(end = 4.dp)
                )
            }
            Box{
                CircularIndicator(
                    sizeCircular = 1.25f,
                    indicatorValue = temperature,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                    foregroundIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
                    label = "Temperature"
                    )
                CircularIndicator(
                    sizeCircular = 2f,
                    indicatorValue = humidity,
                    maxIndicatorValue = 100,
                    backgroundIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                    foregroundIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
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
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    contentDescription: String,
    valueIndicator: Double?,
    modifier: Modifier = Modifier,
    symbol: String
) {
    ElevatedCard(
        modifier = Modifier
            .width(126.dp)
            .height(70.dp),
        shape = RoundedCornerShape(21.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor),

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
                modifier = Modifier.padding(top = 3.dp),
                color = MaterialTheme.colorScheme.onBackground
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
                    ),
                    color = MaterialTheme.colorScheme.onBackground
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
            .offset(y = 10.dp, x = (-10).dp)
            .size(canvasSize)

            .drawBehind {
                val componentSize = size / sizeCircular
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
@Preview
@Composable
fun LazyAboutControl(viewModel: DashBoardMonitoringViewModel = viewModel()) {
    val state by viewModel.stateControl.collectAsStateWithLifecycle()
    val fan = state?.Fan
    val heater = state?.Heater
    val sprayer = state?.Sprayer
    var fanCondition = viewModel.condition(fan)
    var sprayerCondition = viewModel.condition(sprayer)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding = PaddingValues(horizontal = 25.dp)
    ) {
        item { AboutControlCard(
            title = "Kipas",
            value = fanCondition,
            icon = Icons.Filled.Air
        ) }
        item { AboutControlCard(
            title = "Heater",
            value = "$heater %",
            icon = Icons.Filled.LocalFireDepartment
        ) }
        item { AboutControlCard(
            title = "Sprayer",
            value = sprayerCondition,
            icon = Icons.Filled.WaterDrop
        ) }
    }
}
//@Preview
@Composable
fun AboutControlCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    ElevatedCard(
        modifier = Modifier
            .width(120.dp)
            .height(104.dp),
        shape = RoundedCornerShape(21.dp),
        //colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 5.dp))
            Box(
                //verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "",
                    modifier = Modifier
                        .width(88.dp)
                        .height(88.dp)
                        .offset(x = -40.dp)
                        .alpha(0.4f)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.displaySmall
                       
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440", showSystemUi = true)
@Composable
fun GreetingPreview() {

    //DashBoardMenu({},{},userData = )
}