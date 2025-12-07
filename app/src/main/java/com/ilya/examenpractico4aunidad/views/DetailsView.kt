package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.components.FavoriteButton
import com.ilya.examenpractico4aunidad.components.PokemonImage
import com.ilya.examenpractico4aunidad.components.PokemonInfoRow
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.PokemonViewModel

@Composable
fun DetailsView(viewModel: PokemonViewModel, navController: NavController, id: String) {
    val isLoading by viewModel.isPokemonDetailLoading.collectAsState()
    val isFavorite by viewModel.isCurrentPokemonFavorite.collectAsState()

    LaunchedEffect(id) {
        viewModel.getPokemonById(id)
        viewModel.checkIfFavorite(id)
    }

    if (!isLoading) {
        Scaffold(
            topBar = {
                CenterAppBar(
                    name = viewModel.state.name,
                    containerColor = MaterialTheme.colorScheme.surface,
                    onNavigationClick = {
                        navController.navigate(Constants.HOME_ROUTE) {
                            popUpTo(Constants.HOME_ROUTE) { inclusive = true }
                        }
                    },
                    onActionButtonClick = {
                        navController.navigate(Constants.SEARCH_VIEW)
                    }
                )
            },
            floatingActionButton = {
                FavoriteButton(
                    isFavorite = isFavorite,
                    onToggleFavorite = {
                        viewModel.toggleFavorite()
                    }
                )
            }
        ) {
            ContentDetailsView(it, viewModel)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ContentDetailsView(
    paddingValues: PaddingValues,
    viewModel: PokemonViewModel,
) {
    val state = viewModel.state
    val scroll = rememberScrollState(0)

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(scroll)
    ) {
        PokemonImage(imageUrl = state.image)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                state.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (state.isMega) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "MEGA EVOLUTION",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PokemonInfoRow("Height", "${state.height / 10.0} m")
            PokemonInfoRow("Weight", "${state.weight / 10.0} kg")
            PokemonInfoRow("Types", state.types.joinToString(", "))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Abilities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            state.abilities.forEach { ability ->
                Text(
                    "• $ability",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Base Stats",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            state.stats.forEach { stat ->
                PokemonInfoRow(stat.name, stat.value.toString())
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}