package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.components.CharacterImage
import com.ilya.examenpractico4aunidad.components.CharacterInfoRow
import com.ilya.examenpractico4aunidad.components.FavoriteButton
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.CharactersViewModel

@Composable
fun DetailsView(viewModel: CharactersViewModel, navController: NavController, id: String) {
    val isLoading by viewModel.isCharacterLoading.collectAsState()
    val isFavorite by viewModel.isCurrentCharacterFavorite.collectAsState()

    LaunchedEffect(id) {
        viewModel.getCharacterById(id)
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
    viewModel: CharactersViewModel,
) {
    val state = viewModel.state
    val scroll = rememberScrollState(0)

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(scroll)
    ) {
        CharacterImage(imageUrl = state.image)

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

            if (state.japaneseName.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    state.japaneseName,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CharacterInfoRow("Favorites", state.favorites.toString())

            if (state.about.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    state.about,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}