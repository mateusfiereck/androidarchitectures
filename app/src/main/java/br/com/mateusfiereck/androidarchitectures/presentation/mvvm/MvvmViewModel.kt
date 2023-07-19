package br.com.mateusfiereck.androidarchitectures.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mateusfiereck.androidarchitectures.core.SingleLiveEvent
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import br.com.mateusfiereck.androidarchitectures.domain.repository.CharacterRepository
import br.com.mateusfiereck.androidarchitectures.domain.usecase.IsFeatureEnableUseCase
import kotlinx.coroutines.launch

class MvvmViewModel(
    private val repository: CharacterRepository,
    private val isFeatureEnableUseCase: IsFeatureEnableUseCase,
) : ViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showSuccess = MutableLiveData<CharacterModel>()
    val showSuccess: LiveData<CharacterModel> = _showSuccess

    private val _showError = MutableLiveData<Throwable>()
    val showError: LiveData<Throwable> = _showError

    private val _showDialog = SingleLiveEvent<CharacterModel.Origin?>()
    val showDialog: LiveData<CharacterModel.Origin?> = _showDialog

    private val _buttonEnable = MutableLiveData<Boolean>()
    val buttonEnable: LiveData<Boolean> = _buttonEnable

    private var characterModel: CharacterModel? = null

    init {
        getCharacter()
        checkButton()
    }

    private fun checkButton() {
        _buttonEnable.value = isFeatureEnableUseCase.isEnabled("button")
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
        _showDialog.value = characterModel?.origin
    }
}