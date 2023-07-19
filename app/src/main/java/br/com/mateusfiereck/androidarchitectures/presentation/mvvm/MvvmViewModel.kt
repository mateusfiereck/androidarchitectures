package br.com.mateusfiereck.androidarchitectures.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import kotlinx.coroutines.launch

class MvvmViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showSuccess = MutableLiveData<CharacterModel>()
    val showSuccess: LiveData<CharacterModel> = _showSuccess

    private val _showError = MutableLiveData<Throwable>()
    val showError: LiveData<Throwable> = _showError

    private var characterModel: CharacterModel? = null

    init {
        getCharacter()
    }

    fun getCharacter() {
        viewModelScope.launch {
            runCatching {
                _showLoading.value = true
                repository.getCharacter()
            }.onSuccess { model ->
                characterModel = model
                _showLoading.value = false
                _showSuccess.value = model
            }.onFailure { throwable ->
                _showLoading.value = false
                _showError.value = throwable
            }
        }
    }

    fun onSeeOriginClick() {
        // todo
    }
}