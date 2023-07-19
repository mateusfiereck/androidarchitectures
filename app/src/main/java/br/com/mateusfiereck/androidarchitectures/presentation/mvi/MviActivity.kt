package br.com.mateusfiereck.androidarchitectures.presentation.mvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.mateusfiereck.androidarchitectures.R
import br.com.mateusfiereck.androidarchitectures.databinding.CharacterActivityBinding
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MviActivity : AppCompatActivity() {

    private val viewModel: MviViewModel by viewModel()
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
            viewModel.onViewIntent(MviViewModel.ViewIntent.OnSeeOriginClick)
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(this) { viewState ->
            binding.progressLoading.isVisible = viewState.isLoading
            binding.layoutContent.isVisible = viewState.isSuccess
            binding.textError.isVisible = viewState.isError

            if (viewState.isSuccess) {
                binding.textName.text = getString(R.string.text_character_name, viewState.characterName)
                binding.textStatus.text = getString(R.string.text_character_status, viewState.characterStatus)
                binding.textSpecies.text = getString(R.string.text_character_specie, viewState.characterSpecies)
            }
        }

        viewModel.viewEvent.observe(this) { viewAction ->
            when (viewAction) {
                is MviViewModel.ViewEvent.ShowOriginDialog -> {
                    showOriginDialog(viewAction.origin)
                }
                is MviViewModel.ViewEvent.ShowSnackbar -> {
                    // todo
                }
            }
        }
    }

    private fun showOriginDialog(origin: CharacterModel.Origin?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.text_local))
            .setMessage(origin?.name)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }
}