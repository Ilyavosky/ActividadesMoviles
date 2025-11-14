package com.ilya.students_unidad3.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavManager(){
    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = "home"
    ){
        composable("dashboard"){

        }

        composable("calculator"){

        }

        composable("home"){
//            HomeScreen(navController)
        }
    }
}