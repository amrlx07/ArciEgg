package com.arcadia.arciegg

import android.content.Context
import android.content.IntentSender
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arcadia.arciegg.googleAuth.GoogleAuthUiClient
import com.arcadia.arciegg.screen.DashBoardMenu
import com.arcadia.arciegg.screen.LoginScreen
import com.arcadia.arciegg.screen.MainScreen
import com.arcadia.arciegg.screen.MonitoringScreen
import com.arcadia.arciegg.viewModel.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainContent(
    googleAuthUiClient: GoogleAuthUiClient,
    lifecycleScope: CoroutineScope,
    applicationContext: Context
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "sign_in") {
        composable("sign_in") {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    //ganti route yang sesuai
                    //sementara memakai dashboard screen untuk
                    navController.navigate("Dashboard")
                }
            }
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == ComponentActivity.RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )
            LaunchedEffect(key1 = state.isSignSuccesful) {
                if (state.isSignSuccesful) {
                    Toast.makeText(
                        applicationContext,
                        "Berhasil Login",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("DashBoard")
                    viewModel.resetState()
                }
            }

            LoginScreen(
                state = state,
                onSignUp = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
        composable("Dashboard") {
            MainScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            applicationContext,
                            "Berhasil Keluar",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    }
                },
                detailScreen = {
                    navController.navigate("DetailMonitoring")
                },
                onNotication = {}
            )
        }
        composable("DetailMonitoring"){
            MonitoringScreen(
                dashboardScreen = {navController.popBackStack()}
            )
        }
    }
}