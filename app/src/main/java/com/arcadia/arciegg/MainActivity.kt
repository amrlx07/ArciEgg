package com.arcadia.arciegg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.arcadia.arciegg.googleAuth.GoogleAuthUiClient
import com.arcadia.arciegg.screen.DashBoardMenu
import com.arcadia.arciegg.screen.MonitoringScreen
import com.arcadia.arciegg.ui.theme.ArciEggTheme
import com.google.android.gms.auth.api.identity.Identity


class MainActivity : ComponentActivity() {

    private val googleAuthClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ArciEggTheme {
                //MonitoringScreen()
                MainContent(googleAuthUiClient = googleAuthClient, lifecycleScope = lifecycleScope, applicationContext = applicationContext)
                //LineScreenMain()
                //LoginScreen(onSignUp = {}, imageResource = R.drawable.flip_2)
                /* A surface container using the 'background' color from the theme
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
            }
        }
    }
}

