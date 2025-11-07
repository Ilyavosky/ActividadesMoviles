package com.ilya.examensegundaunidad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilya.examensegundaunidad.views.DashboardView
import com.ilya.examensegundaunidad.views.FormView
import com.ilya.examensegundaunidad.views.ThemeView

@Composable
fun NavManager() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {

        composable("dashboard") {
            DashboardView(navController = navController)
        }

        composable("theme_view") {
            ThemeView(navController = navController)
        }

        composable("form_view") {
            FormView(navController = navController)
        }
    }
}