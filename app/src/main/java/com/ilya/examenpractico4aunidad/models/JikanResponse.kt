package com.ilya.examenpractico4aunidad.models

data class JikanCharacterResponse(
    val data: Character
)

data class JikanCharactersResponse(
    val data: List<JikanCharacterData>,
    val pagination: Pagination
)

data class JikanCharacterData(
    val mal_id: Int,
    val url: String,
    val images: CharacterImages,
    val name: String,
    val name_kanji: String?,
    val nicknames: List<String>?,
    val favorites: Int,
    val about: String?
) {
    fun toCharacter(): Character {
        return Character(
            id = mal_id.toString(),
            name = name,
            japaneseName = name_kanji,
            image = images.jpg.image_url,
            about = about,
            favorites = favorites,
            url = url
        )
    }
}

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val current_page: Int,
    val items: PaginationItems
)

data class PaginationItems(
    val count: Int,
    val total: Int,
    val per_page: Int
)