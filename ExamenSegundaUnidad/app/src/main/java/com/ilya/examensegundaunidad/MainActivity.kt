package com.ilya.examensegundaunidad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ilya.examensegundaunidad.navigation.NavManager
import com.ilya.examensegundaunidad.ui.theme.ExamenSegundaUnidadTheme
import com.ilya.examensegundaunidad.viewmodels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()

            ExamenSegundaUnidadTheme(darkTheme = isDarkMode) {
                NavManager()
            }
        }
    }
}