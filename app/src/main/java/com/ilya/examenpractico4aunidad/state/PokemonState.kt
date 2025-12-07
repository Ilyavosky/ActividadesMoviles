package com.ilya.examenpractico4aunidad.state

import com.ilya.examenpractico4aunidad.models.PokemonStat

data class PokemonState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val types: List<String> = emptyList(),
    val abilities: List<String> = emptyList(),
    val stats: List<PokemonStat> = emptyList(),
    val isMega: Boolean = false,
    val baseForm: String? = null
)