package com.ilya.examenpractico4aunidad.models

data class TypeWeakness(
    val type: String,
    val multiplier: Float,
    val affectedPokemon: List<String>
)

data class TeamAnalysis(
    val weaknesses: List<TypeWeakness>,
    val resistances: List<TypeWeakness>,
    val immunities: List<TypeWeakness>
)

object TypeChart {
    private val typeEffectiveness = mapOf(
        "Normal" to mapOf("Fighting" to 2f, "Ghost" to 0f),
        "Fire" to mapOf("Water" to 2f, "Ground" to 2f, "Rock" to 2f, "Fire" to 0.5f, "Grass" to 0.5f, "Ice" to 0.5f, "Bug" to 0.5f, "Steel" to 0.5f, "Fairy" to 0.5f),
        "Water" to mapOf("Electric" to 2f, "Grass" to 2f, "Fire" to 0.5f, "Water" to 0.5f, "Ice" to 0.5f, "Steel" to 0.5f),
        "Electric" to mapOf("Ground" to 2f, "Electric" to 0.5f, "Flying" to 0.5f, "Steel" to 0.5f),
        "Grass" to mapOf("Fire" to 2f, "Ice" to 2f, "Poison" to 2f, "Flying" to 2f, "Bug" to 2f, "Water" to 0.5f, "Electric" to 0.5f, "Grass" to 0.5f, "Ground" to 0.5f),
        "Ice" to mapOf("Fire" to 2f, "Fighting" to 2f, "Rock" to 2f, "Steel" to 2f, "Ice" to 0.5f),
        "Fighting" to mapOf("Flying" to 2f, "Psychic" to 2f, "Fairy" to 2f, "Bug" to 0.5f, "Rock" to 0.5f, "Dark" to 0.5f),
        "Poison" to mapOf("Ground" to 2f, "Psychic" to 2f, "Grass" to 0.5f, "Fighting" to 0.5f, "Poison" to 0.5f, "Bug" to 0.5f, "Fairy" to 0.5f),
        "Ground" to mapOf("Water" to 2f, "Grass" to 2f, "Ice" to 2f, "Poison" to 0.5f, "Rock" to 0.5f, "Electric" to 0f),
        "Flying" to mapOf("Electric" to 2f, "Ice" to 2f, "Rock" to 2f, "Grass" to 0.5f, "Fighting" to 0.5f, "Bug" to 0.5f, "Ground" to 0f),
        "Psychic" to mapOf("Bug" to 2f, "Ghost" to 2f, "Dark" to 2f, "Fighting" to 0.5f, "Psychic" to 0.5f),
        "Bug" to mapOf("Fire" to 2f, "Flying" to 2f, "Rock" to 2f, "Grass" to 0.5f, "Fighting" to 0.5f, "Ground" to 0.5f),
        "Rock" to mapOf("Water" to 2f, "Grass" to 2f, "Fighting" to 2f, "Ground" to 2f, "Steel" to 2f, "Normal" to 0.5f, "Fire" to 0.5f, "Poison" to 0.5f, "Flying" to 0.5f),
        "Ghost" to mapOf("Ghost" to 2f, "Dark" to 2f, "Poison" to 0.5f, "Bug" to 0.5f, "Normal" to 0f, "Fighting" to 0f),
        "Dragon" to mapOf("Ice" to 2f, "Dragon" to 2f, "Fairy" to 2f, "Fire" to 0.5f, "Water" to 0.5f, "Electric" to 0.5f, "Grass" to 0.5f),
        "Dark" to mapOf("Fighting" to 2f, "Bug" to 2f, "Fairy" to 2f, "Ghost" to 0.5f, "Dark" to 0.5f, "Psychic" to 0f),
        "Steel" to mapOf("Fire" to 2f, "Fighting" to 2f, "Ground" to 2f, "Normal" to 0.5f, "Grass" to 0.5f, "Ice" to 0.5f, "Flying" to 0.5f, "Psychic" to 0.5f, "Bug" to 0.5f, "Rock" to 0.5f, "Dragon" to 0.5f, "Steel" to 0.5f, "Fairy" to 0.5f, "Poison" to 0f),
        "Fairy" to mapOf("Poison" to 2f, "Steel" to 2f, "Fighting" to 0.5f, "Bug" to 0.5f, "Dark" to 0.5f, "Dragon" to 0f)
    )

    fun calculateTypeEffectiveness(pokemonTypes: List<String>): Map<String, Float> {
        val effectiveness = mutableMapOf<String, Float>()

        val allTypes = listOf("Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy")

        allTypes.forEach { attackType ->
            var multiplier = 1f
            pokemonTypes.forEach { defenseType ->
                val typeChart = typeEffectiveness[defenseType] ?: emptyMap()
                multiplier *= typeChart[attackType] ?: 1f
            }
            if (multiplier != 1f) {
                effectiveness[attackType] = multiplier
            }
        }

        return effectiveness
    }
}