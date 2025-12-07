package com.ilya.examenpractico4aunidad.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ilya.examenpractico4aunidad.models.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM favorite_pokemon ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<Pokemon>>

    @Query("SELECT * FROM favorite_pokemon WHERE id = :pokemonId")
    suspend fun getFavoriteById(pokemonId: String): Pokemon?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(pokemon: Pokemon)

    @Delete
    suspend fun deleteFavorite(pokemon: Pokemon)

    @Query("DELETE FROM favorite_pokemon WHERE id = :pokemonId")
    suspend fun deleteFavoriteById(pokemonId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_pokemon WHERE id = :pokemonId)")
    suspend fun isFavorite(pokemonId: String): Boolean
}