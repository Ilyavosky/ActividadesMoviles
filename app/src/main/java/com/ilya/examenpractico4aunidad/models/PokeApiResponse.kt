package com.ilya.examenpractico4aunidad.models

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasic>
)

data class PokemonBasic(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeSlot>,
    val abilities: List<PokemonAbilitySlot>,
    val stats: List<PokemonStatSlot>,
    val species: NamedResource
)

data class PokemonSprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class PokemonTypeSlot(
    val type: NamedResource
)

data class PokemonAbilitySlot(
    val ability: NamedResource
)

data class PokemonStatSlot(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: NamedResource
)

data class NamedResource(
    val name: String,
    val url: String
)

data class PokemonSpeciesResponse(
    val id: Int,
    val name: String,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    val varieties: List<PokemonVariety>
)

data class PokemonVariety(
    @SerializedName("is_default")
    val isDefault: Boolean,
    val pokemon: NamedResource
)

fun PokemonDetailResponse.toPokemon(): Pokemon {
    val imageUrl = sprites.other?.officialArtwork?.frontDefault
        ?: sprites.frontDefault
        ?: ""

    val isMegaEvolution = name.contains("mega", ignoreCase = true)
    val baseFormName = if (isMegaEvolution) {
        name.replace("-mega", "").replace("-x", "").replace("-y", "")
    } else null

    return Pokemon(
        id = id.toString(),
        name = name.replace("-", " ").split(" ")
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
        image = imageUrl,
        height = height,
        weight = weight,
        types = types.map { it.type.name.replaceFirstChar { char -> char.uppercase() } },
        abilities = abilities.map { it.ability.name.replace("-", " ")
            .split(" ").joinToString(" ") { word -> word.replaceFirstChar { char -> char.uppercase() } } },
        stats = stats.map {
            PokemonStat(
                name = it.stat.name.replace("-", " ")
                    .split(" ").joinToString(" ") { word -> word.replaceFirstChar { char -> char.uppercase() } },
                value = it.baseStat
            )
        },
        isMega = isMegaEvolution,
        baseForm = baseFormName
    )
}