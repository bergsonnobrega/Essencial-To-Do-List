package com.bergsonnobrega.essenciallenil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bergsonnobrega.essenciallenil.ui.AppNavigation
import com.bergsonnobrega.essenciallenil.ui.theme.EssencialLenilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EssencialLenilTheme {
                AppNavigation()
            }
        }
    }
}