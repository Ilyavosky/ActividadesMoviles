package com.ilya.examenpractico4aunidad.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ilya.examenpractico4aunidad.components.CenterAppBar
import com.ilya.examenpractico4aunidad.models.Pokemon
import com.ilya.examenpractico4aunidad.utils.Constants
import com.ilya.examenpractico4aunidad.viewmodels.PokemonViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBuilderView(viewModel: PokemonViewModel, navController: NavController) {
    val team by viewModel.team.collectAsState()
    val teamAnalysis by viewModel.teamAnalysis.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAppBar(
                name = "Team Builder",
                containerColor = MaterialTheme.colorScheme.surface,
                onNavigationClick = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            if (team.size < 6) {
                FloatingActionButton(
                    onClick = {
                        showBottomSheet = true
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, "Add Pokémon")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(rememberScrollState())
        ) {
            TeamSlots(team = team, onRemove = { viewModel.removePokemonFromTeam(it) })

            if (team.isNotEmpty()) {
                Button(
                    onClick = { viewModel.clearTeam() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Clear Team")
                }
            }

            teamAnalysis?.let { analysis ->
                TeamAnalysisSection(analysis)
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                PokemonSelectionSheet(
                    favorites = favorites,
                    onSelect = { pokemon ->
                        viewModel.addPokemonToTeam(pokemon)
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    },
                    onClose = {
                        scope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TeamSlots(team: List<Pokemon>, onRemove: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Your Team (${team.size}/6)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 0 until 6) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .border(
                            2.dp,
                            if (i < team.size) MaterialTheme.colorScheme.primary else Color.Gray,
                            RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (i < team.size) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = team[i].image,
                                contentDescription = team[i].name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            IconButton(
                                onClick = { onRemove(team[i]) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(24.dp)
                                    .background(Color.Red, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Remove",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    } else {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Empty slot",
                            tint = Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        if (team.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            team.forEach { pokemon ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pokemon.image,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(50.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                pokemon.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                pokemon.types.joinToString(", "),
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

@Composable
fun TeamAnalysisSection(analysis: com.ilya.examenpractico4aunidad.models.TeamAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Team Analysis",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (analysis.weaknesses.isNotEmpty()) {
            WeaknessSection(
                title = "Weaknesses",
                weaknesses = analysis.weaknesses,
                color = Color(0xFFe57373)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (analysis.resistances.isNotEmpty()) {
            WeaknessSection(
                title = "Resistances",
                weaknesses = analysis.resistances,
                color = Color(0xFF6046d8)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (analysis.immunities.isNotEmpty()) {
            WeaknessSection(
                title = "Immunities",
                weaknesses = analysis.immunities,
                color = Color(0xFFe5da7f)
            )
        }
    }
}

@Composable
fun WeaknessSection(
    title: String,
    weaknesses: List<com.ilya.examenpractico4aunidad.models.TypeWeakness>,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            weaknesses.forEach { weakness ->
                TypeWeaknessItem(weakness, color)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TypeWeaknessItem(
    weakness: com.ilya.examenpractico4aunidad.models.TypeWeakness,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                weakness.type,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                "×${weakness.multiplier}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Text(
            "Affects: ${weakness.affectedPokemon.joinToString(", ")}",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun PokemonSelectionSheet(
    favorites: List<Pokemon>,
    onSelect: (Pokemon) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Select a Pokémon",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, "Close")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (favorites.isEmpty()) {
            Text(
                "No favorites yet. Add some Pokémon to favorites first!",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.height(400.dp)
            ) {
                items(favorites) { pokemon ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onSelect(pokemon) },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = pokemon.image,
                                contentDescription = pokemon.name,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    pokemon.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    pokemon.types.joinToString(", "),
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
}