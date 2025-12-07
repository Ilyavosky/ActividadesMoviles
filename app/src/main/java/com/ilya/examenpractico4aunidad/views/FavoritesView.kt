package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilya.examenpractico4aunidad.components.CardFavoriteCharacter
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.CharactersViewModel

@Composable
fun FavoritesView(viewModel: CharactersViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAppBar(
                name = "Favorites",
                containerColor = MaterialTheme.colorScheme.surface,
                onNavigationClick = {
                    navController.popBackStack()
                },
                onActionButtonClick = {
                    navController.navigate(Constants.SEARCH_VIEW)
                }
            )
        }
    ) {
        ContentFavoritesView(viewModel, it, navController)
    }
}

@Composable
fun ContentFavoritesView(
    viewModel: CharactersViewModel,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val favorites by viewModel.favorites.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues)
    ) {
        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No favorites yet",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = "Add characters to favorites from their details page",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            LazyColumn {
                items(favorites) { character ->
                    CardFavoriteCharacter(
                        character = character,
                        onClick = {
                            navController.navigate("${Constants.DETAILS_VIEW}/${character.id}")
                        },
                        onDelete = {
                            viewModel.removeFavoriteById(character.id)
                        }
                    )
                }
            }
        }
    }
}