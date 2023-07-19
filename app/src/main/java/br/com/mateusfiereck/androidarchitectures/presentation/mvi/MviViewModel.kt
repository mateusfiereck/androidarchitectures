package br.com.mateusfiereck.androidarchitectures.presentation.mvi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.core.SingleLiveEvent
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.launch

class MviViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState> = _viewState

    private val _viewEvent = SingleLiveEvent<ViewEvent>()
    val viewEvent: LiveData<ViewEvent> = _viewEvent

    private var characterModel: CharacterModel? = null

    init {
        onViewIntent(ViewIntent.Init)
    }

    fun onViewIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.Init -> getCharacter()
            ViewIntent.OnSeeOriginClick -> onSeeOriginClick()
        }
    }

    private fun getCharacter() {
        viewModelScope.launch {
            runCatching {
                _viewState.value = viewState.value?.setLoading()
                repository.getCharacter()
            }.onSuccess { model ->
                characterModel = model
                _viewState.value = viewState.value?.setSuccess(model)
            }.onFailure {
                _viewState.value = viewState.value?.setError()
            }
        }
    }

    private fun onSeeOriginClick() {
        _viewEvent.value = ViewEvent.ShowOriginDialog(characterModel?.origin)
        _viewEvent.value = ViewEvent.ShowSnackbar
    }

    sealed class ViewIntent {
        object Init : ViewIntent()
        object OnSeeOriginClick : ViewIntent()
    }

    sealed class ViewEvent {
        data class ShowOriginDialog(
            val origin: CharacterModel.Origin?
        ) : ViewEvent()

        object ShowSnackbar: ViewEvent()
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isError: Boolean = false,
        val characterName: String = "",
        val characterStatus: String = "",
        val characterSpecies: String = "",
        val viewEvent: ViewEvent? = null,
    ) {

        fun setLoading() = copy(
            isLoading = true,
            isSuccess = false,
            isError = false,
        )

        fun setSuccess(model: CharacterModel) = copy(
            isLoading = false,
            isSuccess = true,
            isError = false,
            characterName = model.name,
            characterStatus = model.status,
            characterSpecies = model.species,
        )

        fun setError() = copy(
            isLoading = false,
            isSuccess = false,
            isError = true,
        )
    }
}