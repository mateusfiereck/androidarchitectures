package br.com.mateusfiereck.androidarchitectures.di

import br.com.mateusfiereck.androidarchitectures.domain.usecase.IsFeatureEnableUseCase
import org.koin.dsl.module

object DomainModule {

    val modules = module {
        factory { IsFeatureEnableUseCase() }
    }
}
