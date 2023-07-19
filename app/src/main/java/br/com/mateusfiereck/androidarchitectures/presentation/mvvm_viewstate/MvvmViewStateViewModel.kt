package br.com.mateusfiereck.androidarchitectures.presentation.mvvm_viewstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.launch

class MvvmViewStateViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _viewState = MutableLiveData(ViewState())
    val viewState: LiveData<ViewState> = _viewState

    private var characterModel: CharacterModel? = null

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch {
            runCatching {
                _viewState.value = viewState.value?.copy(
                    isLoading = true,
                    isSuccess = false,
                    isError = false,
                )
                repository.getCharacter()
            }.onSuccess { model ->
                characterModel = model
                _viewState.value = viewState.value?.copy(
                    isLoading = false,
                    isSuccess = true,
                    isError = false,
                    characterName = model.name,
                    characterStatus = model.status,
                    characterSpecies = model.species,
                )
            }.onFailure {
                _viewState.value = viewState.value?.copy(
                    isLoading = false,
                    isSuccess = false,
                    isError = true,
                )
            }
        }
    }

    fun onSeeOriginClick() {
        // todo
    }

    data class ViewState(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isError: Boolean = false,
        val characterName: String = "",
        val characterStatus: String = "",
        val characterSpecies: String = "",
    )
}
