package br.com.mateusfiereck.androidarchitectures.di

import br.com.mateusfiereck.androidarchitectures.data.api.CharacterApi
import br.com.mateusfiereck.androidarchitectures.data.mapper.CharacterMapper
import br.com.mateusfiereck.androidarchitectures.data.repository.CharacterRepositoryImpl
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import org.koin.dsl.module

object DataModule {

    val modules = module {
        single { CharacterApi() }
        factory<CharacterRepository> { CharacterRepositoryImpl(api = get(), mapper = CharacterMapper()) }
    }
}
