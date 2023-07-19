package br.com.mateusfiereck.androidarchitectures.data.mapper

import br.com.mateusfiereck.androidarchitectures.data.response.CharacterResponse
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel

class CharacterMapper {

    fun map(response: CharacterResponse) = CharacterModel(
        id = response.id,
        name = response.name,
        status = response.status,
        species = response.species,
        origin = CharacterModel.Origin(
            name = response.origin.name,
            url = response.origin.url,
        )
    )
}
