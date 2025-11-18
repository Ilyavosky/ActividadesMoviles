package com.ilya.students_unidad3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ilya.students_unidad3.screens.MainScreen
import com.ilya.students_unidad3.ui.theme.Students_unidad3Theme
import com.ilya.students_unidad3.viewmodel.ThemeViewModel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()

            Students_unidad3Theme(
                darkTheme = isDarkMode
            ) {
                MainScreen(themeViewModel = themeViewModel)
            }
        }
    }
}