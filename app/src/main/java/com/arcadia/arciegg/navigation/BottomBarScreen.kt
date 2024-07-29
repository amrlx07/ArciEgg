package com.arcadia.arciegg.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
     val route: String,
     val title: String,
     val icon: ImageVector
 ) {
     object DashBoard: BottomBarScreen(
         route = "dashboard",
         title = "Menu",
         icon = Icons.Default.Home
     )
    object LineChart: BottomBarScreen(
        route = "linegraph",
        title = "Grafik",
        icon = Icons.Default.AreaChart
    )
}