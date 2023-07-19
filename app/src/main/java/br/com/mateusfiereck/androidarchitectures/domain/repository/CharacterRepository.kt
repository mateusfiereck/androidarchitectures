package br.com.mateusfiereck.androidarchitectures.domain.repository

import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel

interface CharacterRepository {

    suspend fun getCharacter(): CharacterModel
}