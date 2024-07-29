package com.arcadia.arciegg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arcadia.arciegg.screen.DashBoardMenu
import com.arcadia.arciegg.screen.LineScreenMain
import com.arcadia.arciegg.uiState.UserData

@Composable
fun BottomNavGraph(navController: NavHostController, userData: UserData?, onSignOut : () -> Unit, onNotication: () -> Unit,detailScreen: () -> Unit) {
    NavHost(navController = navController, startDestination = BottomBarScreen.DashBoard.route) {
        composable(route = BottomBarScreen.DashBoard.route) {
            DashBoardMenu(
                userData = userData,
                onSignOut = onSignOut,
                //onNotication = onNotication,
                detailScreen = detailScreen
            )
        }
        composable(route = BottomBarScreen.LineChart.route) {
            LineScreenMain()
        }
    }
}