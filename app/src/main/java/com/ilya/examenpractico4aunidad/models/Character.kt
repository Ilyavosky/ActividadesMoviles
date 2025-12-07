package com.ilya.examenpractico4aunidad.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_characters")
data class Character(
    @PrimaryKey
    @SerializedName("mal_id")
    val id: String,

    val name: String,

    @SerializedName("name_kanji")
    val japaneseName: String? = "",

    val image: String,

    val about: String? = "",

    val favorites: Int = 0,

    val url: String = ""
)

data class CharacterImages(
    val jpg: ImageUrl,
    val webp: ImageUrl
)

data class ImageUrl(
    val image_url: String,
    val small_image_url: String? = null
)