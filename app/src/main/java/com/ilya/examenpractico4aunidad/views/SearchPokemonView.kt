package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPokemonView(viewModel: PokemonViewModel, navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    val pokemonSearched by viewModel.pokemonSearched.collectAsState()
    val isLoading by viewModel.isPokemonLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchPokemon("")
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cleanPokemonSearched()
        }
    }

    Surface(color = MaterialTheme.colorScheme.surface) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query,
            onQueryChange = {
                query = it
                if (it.length >= 2 || it.isEmpty()) {
                    viewModel.searchPokemon(query)
                }
            },
            onSearch = {
                keyboardController?.hide()
                viewModel.searchPokemon(query)
            },
            active = active,
            onActiveChange = {
                active = it
                if (!it) {
                    navController.popBackStack()
                }
            },
            placeholder = { Text("Search Pokémon (e.g., pikachu, charizard)...") },
            leadingIcon = {
                Icon(Icons.Default.Search, "Search...")
            },
            trailingIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBackIos, "Back")
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 10.dp)
            ) {
                if (isLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Text(
                                "Searching...",
                                modifier = Modifier.padding(top = 8.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                } else if (pokemonSearched.isEmpty() && query.isNotEmpty()) {
                    item {
                        Text(
                            "No Pokémon found",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                } else if (pokemonSearched.isEmpty() && query.isEmpty()) {
                    item {
                        Text(
                            "Type to search any Pokémon from the National Pokédex",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(pokemonSearched) { pokemon ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("${Constants.DETAILS_VIEW}/${pokemon.id}") {
                                        popUpTo(Constants.HOME_ROUTE)
                                    }
                                }
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Text(
                                pokemon.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                pokemon.types.joinToString(", ") + " • #${pokemon.id}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    }
}