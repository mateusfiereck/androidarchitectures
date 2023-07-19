package br.com.mateusfiereck.androidarchitectures

import android.app.Application
import br.com.mateusfiereck.androidarchitectures.di.DataModule
import br.com.mateusfiereck.androidarchitectures.di.DomainModule
import br.com.mateusfiereck.androidarchitectures.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(DataModule.modules, DomainModule.modules, PresentationModule.modules)
        }
    }
}