package com.ilya.examenpractico4aunidad.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.PokemonViewModel
import com.ilya.examenpractico4aunidad.views.DetailsView
import com.ilya.examenpractico4aunidad.views.FavoritesView
import com.ilya.examenpractico4aunidad.views.HomeView
import com.ilya.examenpractico4aunidad.views.SearchPokemonView
import com.ilya.examenpractico4aunidad.views.TeamBuilderView

@Composable
fun NavManager(pokemonViewModel: PokemonViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Constants.HOME_ROUTE) {
        composable(Constants.HOME_ROUTE) {
            HomeView(pokemonViewModel, navController)
        }

        composable(
            route = "${Constants.DETAILS_VIEW}/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            val id = it.arguments?.getString("id") ?: ""
            DetailsView(pokemonViewModel, navController, id)
        }

        composable(Constants.SEARCH_VIEW) {
            SearchPokemonView(pokemonViewModel, navController)
        }

        composable(Constants.FAVORITES_VIEW) {
            FavoritesView(pokemonViewModel, navController)
        }

        composable(Constants.TEAM_BUILDER_VIEW) {
            TeamBuilderView(pokemonViewModel, navController)
        }
    }
}