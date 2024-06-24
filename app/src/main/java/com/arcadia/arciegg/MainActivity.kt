package com.arcadia.arciegg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arcadia.arciegg.screen.LineScreenMain
import com.arcadia.arciegg.ui.theme.ArciEggTheme
import com.arcadia.arciegg.screen.TabList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArciEggTheme {
                LineScreenMain()
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

