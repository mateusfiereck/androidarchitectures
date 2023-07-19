package br.com.mateusfiereck.androidarchitectures.presentation.mvvm_statemachine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.mateusfiereck.androidarchitectures.R
import br.com.mateusfiereck.androidarchitectures.databinding.CharacterActivityBinding
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MvvmStateMachineActivity : AppCompatActivity() {

    private val viewModel: MvvmStateMachineViewModel by viewModel()
    private lateinit var binding: CharacterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.buttonOrigin.setOnClickListener {
            viewModel.onSeeOriginClick()
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is MvvmStateMachineViewModel.State.Loading -> {
                    showLoading()
                }

                is MvvmStateMachineViewModel.State.Success -> {
                    showSuccess(state.model, state.buttonEnabled)
                }

                is MvvmStateMachineViewModel.State.Error -> {
                    showError()
                }
            }
        }

        viewModel.showDialog.observe(this) {
            showOriginDialog(it)
        }
    }

    private fun showLoading() {
        binding.progressLoading.isVisible = true
        binding.layoutContent.isVisible = false
        binding.textError.isVisible = false
    }

    private fun showSuccess(model: CharacterModel, buttonEnable: Boolean) {
        binding.progressLoading.isVisible = false
        binding.layoutContent.isVisible = true
        binding.textError.isVisible = false

        binding.textName.text = getString(R.string.text_character_name, model.name)
        binding.textStatus.text = getString(R.string.text_character_status, model.status)
        binding.textSpecies.text = getString(R.string.text_character_specie, model.species)
        binding.buttonOrigin.isVisible = buttonEnable
    }

    private fun showError() {
        binding.progressLoading.isVisible = false
        binding.layoutContent.isVisible = false
        binding.textError.isVisible = true
    }

    private fun showOriginDialog(origin: CharacterModel.Origin?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.text_local))
            .setMessage(origin?.name)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }
}
