package br.com.mateusfiereck.androidarchitectures.data.repository

import br.com.mateusfiereck.androidarchitectures.data.api.CharacterApi
import br.com.mateusfiereck.androidarchitectures.data.mapper.CharacterMapper
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CharacterRepositoryImpl(
    private val api: CharacterApi,
    private val mapper: CharacterMapper,
) : CharacterRepository {

    override suspend fun getCharacter(): CharacterModel =
        withContext(Dispatchers.IO) {
            try {
                val result = api.getCharacter()
                delay(2000L)
                mapper.map(result)
            } catch (exception: Exception) {
                throw exception
            }
        }
}
