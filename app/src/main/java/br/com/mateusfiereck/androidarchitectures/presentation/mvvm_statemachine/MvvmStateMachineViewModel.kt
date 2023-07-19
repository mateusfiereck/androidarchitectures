package br.com.mateusfiereck.androidarchitectures.presentation.mvvm_statemachine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.launch

class MvvmStateMachineViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

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
                _state.value = State.Success(model)
            }.onFailure {
                _state.value = State.Error
            }
        }
    }

    fun onSeeOriginClick() {
        // todo
    }

    sealed class State {
        object Loading : State()
        data class Success(val model: CharacterModel) : State()
        object Error : State()
    }
}
