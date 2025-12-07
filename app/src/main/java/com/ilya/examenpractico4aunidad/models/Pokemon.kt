package com.ilya.examenpractico4aunidad.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_pokemon")
@TypeConverters(PokemonConverters::class)
data class Pokemon(
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val isMega: Boolean = false,
    val baseForm: String? = null
)

data class PokemonStat(
    val name: String,
    val value: Int
)

class PokemonConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStatsList(value: List<PokemonStat>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStatsList(value: String): List<PokemonStat> {
        val listType = object : TypeToken<List<PokemonStat>>() {}.type
        return gson.fromJson(value, listType)
    }
}