package com.ilya.students_unidad3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ilya.students_unidad3.navigation.NavManager
import com.ilya.students_unidad3.ui.theme.Students_unidad3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Students_unidad3Theme {
                NavManager()
            }
        }
    }
}