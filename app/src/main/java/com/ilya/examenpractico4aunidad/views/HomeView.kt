package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import com.ilya.examenpractico4aunidad.components.CardCharacter
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.components.Loader
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.CharactersViewModel

@Composable
fun HomeView(viewModel: CharactersViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAppBar(
                name = "JoJo Characters",
                containerColor = MaterialTheme.colorScheme.surface,
                onActionButtonClick = {
                    navController.navigate(Constants.SEARCH_VIEW)
                },
                actionIcon = {
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
            )
        }
    ) {
        ContentHomeView(viewModel, it, navController)
    }
}

@Composable
fun ContentHomeView(
    viewModel: CharactersViewModel,
    paddingValues: PaddingValues,
    navController: NavController
) {
    val charactersPage = viewModel.charactersPage.collectAsLazyPagingItems()
    val loadState = charactersPage.loadState

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
                Text("Error loading characters")
            }
        }
        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(charactersPage.itemCount) { index ->
                    val item = charactersPage[index]
                    if (item != null) {
                        CardCharacter(item) {
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
                            Text("Error loading more characters")
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}