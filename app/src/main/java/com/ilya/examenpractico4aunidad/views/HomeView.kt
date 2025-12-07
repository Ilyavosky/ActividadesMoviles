package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilya.examenpractico4aunidad.components.CardPokemon
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.components.Loader
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.PokemonViewModel

@Composable
fun HomeView(viewModel: PokemonViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAppBar(
                name = "National Pokédex",
                containerColor = MaterialTheme.colorScheme.surface,
                onActionButtonClick = {
                    navController.navigate(Constants.SEARCH_VIEW)
                },
                actionIcon = {
                    Row {
                        IconButton(onClick = {
                            navController.navigate(Constants.TEAM_BUILDER_VIEW)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = "Team Builder",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(onClick = {
                            navController.navigate(Constants.FAVORITES_VIEW)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorites",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) {
        ContentHomeView(viewModel, it, navController)
    }
}

@Composable
fun ContentHomeView(
    viewModel: PokemonViewModel,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val pokemonPage = viewModel.pokemonPage.collectAsLazyPagingItems()
    val loadState = pokemonPage.loadState

    when (loadState.refresh) {
        is LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Loader()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Error loading Pokémon")
            }
        }
        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(pokemonPage.itemCount) { index ->
                    val item = pokemonPage[index]
                    if (item != null) {
                        CardPokemon(item) {
                            navController.navigate("${Constants.DETAILS_VIEW}/${item.id}")
                        }
                    }
                }

                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Column(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Loader()
                            }
                        }
                    }
                    is LoadState.Error -> {
                        item {
                            Text("Error loading more Pokémon")
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}