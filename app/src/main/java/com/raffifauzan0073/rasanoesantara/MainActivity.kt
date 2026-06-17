package com.raffifauzan0073.rasanoesantara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.raffifauzan0073.rasanoesantara.ui.screen.MainScreen
import com.raffifauzan0073.rasanoesantara.ui.theme.RasaNoesantaraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RasaNoesantaraTheme {
                MainScreen()
            }
        }
    }
}
