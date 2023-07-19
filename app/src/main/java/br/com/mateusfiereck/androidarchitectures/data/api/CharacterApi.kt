package br.com.mateusfiereck.androidarchitectures.data.api

import br.com.mateusfiereck.androidarchitectures.data.response.CharacterResponse

class CharacterApi {

    fun getCharacter() = CharacterResponse(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        origin = CharacterResponse.OriginResponse(
            name = "Earth",
            url = "https://rickandmortyapi.com/api/location/1"
        ),
    )
}