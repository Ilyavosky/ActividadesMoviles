package com.ilya.examenpractico4aunidad.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ilya.examenpractico4aunidad.models.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM favorite_characters ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<Character>>

    @Query("SELECT * FROM favorite_characters WHERE id = :characterId")
    suspend fun getFavoriteById(characterId: String): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(character: Character)

    @Delete
    suspend fun deleteFavorite(character: Character)

    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    suspend fun deleteFavoriteById(characterId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_characters WHERE id = :characterId)")
    suspend fun isFavorite(characterId: String): Boolean
}