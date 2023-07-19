package br.com.mateusfiereck.androidarchitectures.presentation.mvvm_statemachine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.core.SingleLiveEvent
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import br.com.mateusfiereck.androidarchitectures.domain.usecase.IsFeatureEnableUseCase
import kotlinx.coroutines.launch

class MvvmStateMachineViewModel(
    private val repository: CharacterRepository,
    private val isFeatureEnableUseCase: IsFeatureEnableUseCase,
) : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val model: CharacterModel, val buttonEnabled: Boolean) : State()
        object Error : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _showDialog = SingleLiveEvent<CharacterModel.Origin?>()
    val showDialog: LiveData<CharacterModel.Origin?> = _showDialog

    private var characterModel: CharacterModel? = null

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch {
            runCatching {
                _state.value = State.Loading
                repository.getCharacter()
            }.onSuccess { model ->
                characterModel = model
                _state.value = State.Success(
                    model,
                    buttonEnabled = isFeatureEnableUseCase.isEnabled("button")
                )
            }.onFailure {
                _state.value = State.Error
            }
        }
    }

    fun onSeeOriginClick() {
        _showDialog.value = characterModel?.origin
    }
}
