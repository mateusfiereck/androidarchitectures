package br.com.mateusfiereck.androidarchitectures.presentation.mvp

import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MvpPresenter(
    private val view: MvpActivity,
    private val repository: CharacterRepository,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + uiContext

    private var characterModel: CharacterModel? = null

    fun getCharacter() {
        launch {
            runCatching {
                view.showLoading()
                repository.getCharacter()
            }.onSuccess { model ->
                characterModel = model
                view.showSuccess(model)
            }.onFailure {
                view.showError()
            }
        }
    }

    fun onSeeOriginClick() {
        view.showOriginDialog(characterModel?.origin)
    }

    fun onDestroy() {
        job.cancel()
    }
}