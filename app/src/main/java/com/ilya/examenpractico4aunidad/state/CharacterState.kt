package com.ilya.examenpractico4aunidad.state

data class CharacterState(
    val id: String = "",
    val name: String = "",
    val japaneseName: String = "",
    val image: String = "",
    val abilities: String = "",
    val nationality: String = "",
    val catchphrase: String = "",
    val chapter: String = "",
    val isLiving: Boolean = true,
    val isHuman: Boolean = true,
    val isFavorite: Boolean = false
)