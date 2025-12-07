package com.ilya.examenpractico4aunidad.repositories

import com.ilya.examenpractico4aunidad.data.ApiPokemon
import com.ilya.examenpractico4aunidad.database.PokemonDao
import com.ilya.examenpractico4aunidad.models.Pokemon
import com.ilya.examenpractico4aunidad.models.toPokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiPokemon: ApiPokemon,
    private val pokemonDao: PokemonDao
) {

    suspend fun getAllPokemon(page: Int = 1): List<Pokemon> {
        return try {
            val offset = (page - 1) * 20
            val response = apiPokemon.getAllPokemon(limit = 20, offset = offset)

            if (response.isSuccessful) {
                val pokemonList = response.body()?.results ?: emptyList()
                val detailedPokemon = mutableListOf<Pokemon>()

                pokemonList.forEach { basicPokemon ->
                    val detailResponse = apiPokemon.getPokemonByName(basicPokemon.name)
                    if (detailResponse.isSuccessful) {
                        detailResponse.body()?.let { detail ->
                            detailedPokemon.add(detail.toPokemon())

                            val speciesResponse = apiPokemon.getPokemonSpecies(detail.id.toString())
                            if (speciesResponse.isSuccessful) {
                                speciesResponse.body()?.varieties?.forEach { variety ->
                                    if (!variety.isDefault) {
                                        val varietyName = variety.pokemon.name
                                        if (varietyName.contains("mega")) {
                                            val megaResponse = apiPokemon.getPokemonByName(varietyName)
                                            if (megaResponse.isSuccessful) {
                                                megaResponse.body()?.let { mega ->
                                                    detailedPokemon.add(mega.toPokemon())
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                detailedPokemon
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getPokemonById(id: String): Pokemon? {
        return try {
            val response = apiPokemon.getPokemonById(id)
            if (response.isSuccessful) {
                response.body()?.toPokemon()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun searchPokemon(query: String): List<Pokemon> {
        return try {
            if (query.isBlank()) {
                emptyList()
            } else {
                val searchResults = mutableListOf<Pokemon>()

                val directResponse = apiPokemon.getPokemonByName(query.lowercase().trim())
                if (directResponse.isSuccessful) {
                    directResponse.body()?.let { pokemon ->
                        searchResults.add(pokemon.toPokemon())

                        val speciesResponse = apiPokemon.getPokemonSpecies(pokemon.id.toString())
                        if (speciesResponse.isSuccessful) {
                            speciesResponse.body()?.varieties?.forEach { variety ->
                                if (!variety.isDefault) {
                                    val varietyName = variety.pokemon.name
                                    if (varietyName.contains("mega")) {
                                        val megaResponse = apiPokemon.getPokemonByName(varietyName)
                                        if (megaResponse.isSuccessful) {
                                            megaResponse.body()?.let { mega ->
                                                searchResults.add(mega.toPokemon())
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return searchResults
                }

                val allResponse = apiPokemon.getAllPokemon(limit = 1500, offset = 0)
                if (allResponse.isSuccessful) {
                    val filtered = allResponse.body()?.results?.filter {
                        it.name.contains(query.lowercase().trim(), ignoreCase = true)
                    }?.take(20) ?: emptyList()

                    filtered.forEach { basicPokemon ->
                        val detailResponse = apiPokemon.getPokemonByName(basicPokemon.name)
                        if (detailResponse.isSuccessful) {
                            detailResponse.body()?.let { detail ->
                                searchResults.add(detail.toPokemon())
                            }
                        }
                    }
                }

                searchResults
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getAllFavorites(): Flow<List<Pokemon>> {
        return pokemonDao.getAllFavorites()
    }

    suspend fun addFavorite(pokemon: Pokemon) {
        pokemonDao.insertFavorite(pokemon)
    }

    suspend fun removeFavorite(pokemon: Pokemon) {
        pokemonDao.deleteFavorite(pokemon)
    }

    suspend fun removeFavoriteById(pokemonId: String) {
        pokemonDao.deleteFavoriteById(pokemonId)
    }

    suspend fun isFavorite(id: String): Boolean {
        return pokemonDao.isFavorite(id)
    }
}