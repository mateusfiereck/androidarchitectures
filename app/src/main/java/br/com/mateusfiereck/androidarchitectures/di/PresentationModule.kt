package br.com.mateusfiereck.androidarchitectures.di

import br.com.mateusfiereck.androidarchitectures.presentation.mvi.MviViewModel
import br.com.mateusfiereck.androidarchitectures.presentation.mvp.MvpPresenter
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm.MvvmViewModel
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm_statemachine.MvvmStateMachineViewModel
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm_viewstate.MvvmViewStateViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {

    val modules = module {
        viewModel { MvvmViewModel(repository = get(), get()) }
        viewModel { MvvmStateMachineViewModel(repository = get(), get()) }
        viewModel { MvvmViewStateViewModel(repository = get(), get()) }
        viewModel { MviViewModel(repository = get()) }

        factory { params ->
            MvpPresenter(
                view = params.get(),
                repository = get(),
            )
        }
    }
}
