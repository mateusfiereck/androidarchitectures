package br.com.mateusfiereck.androidarchitectures.presentation.mvp

import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel

interface MvpViewContract {

    fun showLoading()
    fun showSuccess(model: CharacterModel)
    fun showError()
    fun showOriginDialog(origin: CharacterModel.Origin?)
}