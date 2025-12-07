package com.ilya.examenpractico4aunidad.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ilya.examenpractico4aunidad.data.CharacterDataSource
import com.ilya.examenpractico4aunidad.models.Character
import com.ilya.examenpractico4aunidad.repositories.CharactersRepository
import com.ilya.examenpractico4aunidad.state.CharacterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
): ViewModel() {

    var state by mutableStateOf(CharacterState())
        private set

    private val _charactersSearched = MutableStateFlow<List<Character>>(emptyList())
    val charactersSearched: StateFlow<List<Character>> = _charactersSearched

    private val _isCharactersLoading = MutableStateFlow(false)
    val isCharactersLoading: StateFlow<Boolean> = _isCharactersLoading

    private val _isCharacterLoading = MutableStateFlow(false)
    val isCharacterLoading: StateFlow<Boolean> = _isCharacterLoading

    private val _favorites = MutableStateFlow<List<Character>>(emptyList())
    val favorites: StateFlow<List<Character>> = _favorites

    val charactersPage = Pager(PagingConfig(pageSize = 10)) {
        CharacterDataSource(repository)
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

    fun searchCharacters(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isCharactersLoading.value = true
                _charactersSearched.value = repository.searchCharacters(name) ?: emptyList()
                _isCharactersLoading.value = false
            }
        }
    }

    fun cleanCharactersSearched() {
        _charactersSearched.value = emptyList()
    }

    fun getCharacterById(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isCharacterLoading.value = true
                val result = repository.getCharacterById(id)
                val isFav = repository.isFavorite(id)

                state = state.copy(
                    id = result?.id ?: "",
                    name = result?.name ?: "",
                    japaneseName = result?.japaneseName ?: "",
                    image = result?.image ?: "",
                    abilities = result?.abilities ?: "Unknown",
                    nationality = result?.nationality ?: "Unknown",
                    catchphrase = result?.catchphrase ?: "",
                    chapter = result?.chapter ?: "Unknown",
                    isLiving = result?.isLiving ?: false,
                    isHuman = result?.isHuman ?: false,
                    isFavorite = isFav
                )
                _isCharacterLoading.value = false
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val character = Character(
                    id = state.id,
                    name = state.name,
                    japaneseName = state.japaneseName,
                    image = state.image,
                    abilities = state.abilities,
                    nationality = state.nationality,
                    catchphrase = state.catchphrase,
                    chapter = state.chapter,
                    isLiving = state.isLiving,
                    isHuman = state.isHuman
                )

                if (state.isFavorite) {
                    repository.removeFavorite(character)
                    state = state.copy(isFavorite = false)
                } else {
                    repository.addFavorite(character)
                    state = state.copy(isFavorite = true)
                }
            }
        }
    }

    fun removeFavoriteById(characterId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.removeFavoriteById(characterId)
            }
        }
    }
}