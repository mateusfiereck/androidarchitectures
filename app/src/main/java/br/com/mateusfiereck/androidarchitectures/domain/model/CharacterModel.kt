package br.com.mateusfiereck.androidarchitectures.domain.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val origin: Origin,
) {

    data class Origin(
        val name: String,
        val url: String,
    )
}
