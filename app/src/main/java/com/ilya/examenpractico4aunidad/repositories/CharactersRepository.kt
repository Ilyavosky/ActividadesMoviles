package com.ilya.examenpractico4aunidad.repositories

import com.ilya.examenpractico4aunidad.data.ApiJikan
import com.ilya.examenpractico4aunidad.data.CharacterDao
import com.ilya.examenpractico4aunidad.models.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val apiJikan: ApiJikan,
    private val characterDao: CharacterDao
) {

    suspend fun getAllCharacters(page: Int = 1): List<Character> {
        return try {
            val response = apiJikan.getAllCharacters(page = page, limit = 20)
            if (response.isSuccessful) {
                response.body()?.data?.map { it.toCharacter() } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getCharacterById(id: String): Character? {
        return try {
            val response = apiJikan.getCharacterById(id)
            if (response.isSuccessful) {
                val jikanData = response.body()?.data
                jikanData
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun searchCharacters(query: String): List<Character> {
        return try {
            val response = apiJikan.searchCharacters(query, limit = 50)
            if (response.isSuccessful) {
                response.body()?.data?.map { it.toCharacter() } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getAllFavorites(): Flow<List<Character>> {
        return characterDao.getAllFavorites()
    }

    suspend fun insertFavorite(character: Character) {
        characterDao.insertFavorite(character)
    }

    suspend fun deleteFavorite(character: Character) {
        characterDao.deleteFavorite(character)
    }

    suspend fun isFavorite(id: String): Boolean {
        return characterDao.isFavorite(id)
    }
}