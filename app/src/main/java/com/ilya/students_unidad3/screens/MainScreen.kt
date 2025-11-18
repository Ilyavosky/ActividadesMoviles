package com.ilya.students_unidad3.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ilya.students_unidad3.components.StudentBottomBar
import com.ilya.students_unidad3.navigation.NavManager
import com.ilya.students_unidad3.viewmodel.StudentViewModel
import com.ilya.students_unidad3.viewmodel.ThemeViewModel.ThemeViewModel

@Composable
fun MainScreen(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val studentViewModel: StudentViewModel = viewModel()

    Scaffold(
        bottomBar = { StudentBottomBar(navController = navController) }
    ) { innerPadding ->
        NavManager(
            navController = navController,
            studentViewModel = studentViewModel,
            themeViewModel = themeViewModel
        )
    }
}