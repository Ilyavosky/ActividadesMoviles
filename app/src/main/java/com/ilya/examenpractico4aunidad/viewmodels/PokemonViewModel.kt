package com.ilya.examenpractico4aunidad.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilya.examenpractico4aunidad.data.PokemonDataSource
import com.ilya.examenpractico4aunidad.models.Pokemon
import com.ilya.examenpractico4aunidad.models.TeamAnalysis
import com.ilya.examenpractico4aunidad.models.TypeChart
import com.ilya.examenpractico4aunidad.models.TypeWeakness
import com.ilya.examenpractico4aunidad.repositories.PokemonRepository
import com.ilya.examenpractico4aunidad.state.PokemonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    var state by mutableStateOf(PokemonState())
        private set

    private val _pokemonSearched = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonSearched: StateFlow<List<Pokemon>> = _pokemonSearched

    private val _isPokemonLoading = MutableStateFlow(false)
    val isPokemonLoading: StateFlow<Boolean> = _isPokemonLoading

    private val _isPokemonDetailLoading = MutableStateFlow(false)
    val isPokemonDetailLoading: StateFlow<Boolean> = _isPokemonDetailLoading

    private val _isCurrentPokemonFavorite = MutableStateFlow(false)
    val isCurrentPokemonFavorite: StateFlow<Boolean> = _isCurrentPokemonFavorite

    private val _favorites = MutableStateFlow<List<Pokemon>>(emptyList())
    val favorites: StateFlow<List<Pokemon>> = _favorites

    private val _team = MutableStateFlow<List<Pokemon>>(emptyList())
    val team: StateFlow<List<Pokemon>> = _team

    private val _teamAnalysis = MutableStateFlow<TeamAnalysis?>(null)
    val teamAnalysis: StateFlow<TeamAnalysis?> = _teamAnalysis

    val pokemonPage = Pager(PagingConfig(pageSize = 10)) {
        PokemonDataSource(repository)
    }.flow.cachedIn(viewModelScope)

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favoritesList ->
                _favorites.value = favoritesList
            }
        }
    }

    fun searchPokemon(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isPokemonLoading.value = true
                _pokemonSearched.value = repository.searchPokemon(name)
                _isPokemonLoading.value = false
            }
        }
    }

    fun cleanPokemonSearched() {
        _pokemonSearched.value = emptyList()
    }

    fun getPokemonById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isPokemonDetailLoading.value = true
                val result = repository.getPokemonById(id)

                state = state.copy(
                    id = result?.id ?: "",
                    name = result?.name ?: "",
                    image = result?.image ?: "",
                    height = result?.height ?: 0,
                    weight = result?.weight ?: 0,
                    types = result?.types ?: emptyList(),
                    abilities = result?.abilities ?: emptyList(),
                    stats = result?.stats ?: emptyList(),
                    isMega = result?.isMega ?: false,
                    baseForm = result?.baseForm
                )
                _isPokemonDetailLoading.value = false
            }
        }
    }

    fun checkIfFavorite(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isCurrentPokemonFavorite.value = repository.isFavorite(id)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val pokemon = Pokemon(
                    id = state.id,
                    name = state.name,
                    image = state.image,
                    height = state.height,
                    weight = state.weight,
                    types = state.types,
                    abilities = state.abilities,
                    stats = state.stats,
                    isMega = state.isMega,
                    baseForm = state.baseForm
                )

                if (_isCurrentPokemonFavorite.value) {
                    repository.removeFavorite(pokemon)
                    _isCurrentPokemonFavorite.value = false
                } else {
                    repository.addFavorite(pokemon)
                    _isCurrentPokemonFavorite.value = true
                }
            }
        }
    }

    fun removeFavoriteById(pokemonId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removeFavoriteById(pokemonId)
            }
        }
    }

    fun addPokemonToTeam(pokemon: Pokemon) {
        if (_team.value.size < 6 && !_team.value.any { it.id == pokemon.id }) {
            _team.value = _team.value + pokemon
            analyzeTeam()
        }
    }

    fun removePokemonFromTeam(pokemon: Pokemon) {
        _team.value = _team.value.filter { it.id != pokemon.id }
        analyzeTeam()
    }

    fun clearTeam() {
        _team.value = emptyList()
        _teamAnalysis.value = null
    }

    private fun analyzeTeam() {
        if (_team.value.isEmpty()) {
            _teamAnalysis.value = null
            return
        }

        val typeCount = mutableMapOf<String, MutableList<String>>()

        _team.value.forEach { pokemon ->
            val effectiveness = TypeChart.calculateTypeEffectiveness(pokemon.types)
            effectiveness.forEach { (type, multiplier) ->
                val key = "$type:$multiplier"
                if (!typeCount.containsKey(key)) {
                    typeCount[key] = mutableListOf()
                }
                typeCount[key]?.add(pokemon.name)
            }
        }

        val weaknesses = mutableListOf<TypeWeakness>()
        val resistances = mutableListOf<TypeWeakness>()
        val immunities = mutableListOf<TypeWeakness>()

        typeCount.forEach { (key, pokemonNames) ->
            val parts = key.split(":")
            val type = parts[0]
            val multiplier = parts[1].toFloat()

            when {
                multiplier == 0f -> immunities.add(TypeWeakness(type, multiplier, pokemonNames))
                multiplier > 1f -> weaknesses.add(TypeWeakness(type, multiplier, pokemonNames))
                multiplier < 1f -> resistances.add(TypeWeakness(type, multiplier, pokemonNames))
            }
        }

        _teamAnalysis.value = TeamAnalysis(
            weaknesses = weaknesses.sortedByDescending { it.multiplier },
            resistances = resistances.sortedBy { it.multiplier },
            immunities = immunities
        )
    }
}