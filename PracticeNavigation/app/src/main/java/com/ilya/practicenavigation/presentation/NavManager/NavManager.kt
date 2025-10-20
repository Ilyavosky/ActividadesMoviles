package com.ilya.practicenavigation.presentation.NavManager

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilya.practicenavigation.presentation.views.DetailsView
import com.ilya.practicenavigation.presentation.views.HomeView

@Composable
fun NavManager(navController: NavController, id: Long, name: String, description: String){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ){
        composable("Home") {
            HomeView(navController)
        }
        composable("Details/{id}/{name}/{description}", arguments = listOf(
            navArgument("id"){
                type = NavType.LongType
            },
            navArgument("name"){
                type = NavType.StringType
            },
            navArgument("description"){
                type = NavType.StringType
            }
        )) {
            val id = it.arguments?.getLong("id") ?: 0
            val name = it.arguments?.getString("name") ?: ""
            val description = it.arguments?.getString("description") ?: ""
            DetailsView(navController, id, name, description)
        }
    }
}