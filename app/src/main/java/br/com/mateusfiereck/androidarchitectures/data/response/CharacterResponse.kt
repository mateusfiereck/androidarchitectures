package br.com.mateusfiereck.androidarchitectures.data.response

data class CharacterResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val origin: OriginResponse,
) {

    data class OriginResponse(
        val name: String,
        val url: String,
    )
}
