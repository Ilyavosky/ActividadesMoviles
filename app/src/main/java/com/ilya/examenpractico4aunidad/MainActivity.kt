package com.ilya.examenpractico4aunidad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ilya.examenpractico4aunidad.navigation.NavManager
import com.ilya.examenpractico4aunidad.ui.theme.ExamenPractico4aUnidadTheme
import com.ilya.examenpractico4aunidad.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: CharactersViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            ExamenPractico4aUnidadTheme {
                NavManager(viewModel)
            }
        }
    }
}