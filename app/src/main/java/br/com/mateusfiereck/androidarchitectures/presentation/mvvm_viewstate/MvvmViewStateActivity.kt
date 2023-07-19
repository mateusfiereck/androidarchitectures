package br.com.mateusfiereck.androidarchitectures.presentation.mvvm_viewstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.mateusfiereck.androidarchitectures.R
import br.com.mateusfiereck.androidarchitectures.databinding.CharacterActivityBinding
import br.com.mateusfiereck.androidarchitectures.domain.model.CharacterModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MvvmViewStateActivity : AppCompatActivity() {

    private val viewModel: MvvmViewStateViewModel by viewModel()
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
        viewModel.viewState.observe(this) { viewState ->
            binding.progressLoading.isVisible = viewState.isLoading
            binding.layoutContent.isVisible = viewState.isSuccess
            binding.textError.isVisible = viewState.isError
            binding.buttonOrigin.isVisible = viewState.isButtonEnable

            if (viewState.isSuccess) {
                binding.textName.text = getString(R.string.text_character_name, viewState.characterName)
                binding.textStatus.text = getString(R.string.text_character_status, viewState.characterStatus)
                binding.textSpecies.text = getString(R.string.text_character_specie, viewState.characterSpecies)
            }
            if (viewState.showDialog) {
                showOriginDialog(viewState.origin)
            }
        }
    }

    private fun showOriginDialog(origin: CharacterModel.Origin?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.text_local))
            .setMessage(origin?.name)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setOnDismissListener {
                viewModel.consumeDialog()
            }
            .show()
    }
}