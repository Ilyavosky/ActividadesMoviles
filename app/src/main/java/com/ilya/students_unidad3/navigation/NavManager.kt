package com.ilya.students_unidad3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ilya.students_unidad3.screens.addstudent.AddStudentScreen.AddStudentScreen
import com.ilya.students_unidad3.screens.dashboard.DashboardScreen
import com.ilya.students_unidad3.screens.editstudent.EditStudentScreen
import com.ilya.students_unidad3.screens.statistics.StatisticsScreen
import com.ilya.students_unidad3.viewmodel.StudentViewModel
import com.ilya.students_unidad3.viewmodel.ThemeViewModel.ThemeViewModel

@Composable
fun NavManager(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    themeViewModel: ThemeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Dashboard.route
    ) {
        composable(BottomBarScreen.Dashboard.route) {
            DashboardScreen(
                studentViewModel = studentViewModel,
                themeViewModel = themeViewModel,
                onAddStudentClick = { navController.navigate("add_student") },
                onEditStudentClick = { studentId ->
                    navController.navigate("edit_student/$studentId")
                }
            )
        }
        composable(BottomBarScreen.Statistics.route) {
            StatisticsScreen(viewModel = studentViewModel)
        }
        composable("add_student") {
            AddStudentScreen(
                viewModel = studentViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "edit_student/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId") ?: 0
            EditStudentScreen(
                viewModel = studentViewModel,
                studentId = studentId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}