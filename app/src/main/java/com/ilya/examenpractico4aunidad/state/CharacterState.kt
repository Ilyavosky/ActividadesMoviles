package com.ilya.examenpractico4aunidad.state

data class CharacterState(
    val id: String = "",
    val name: String = "",
    val japaneseName: String = "",
    val image: String = "",
    val about: String = "",
    val favorites: Int = 0,
    val url: String = ""
)