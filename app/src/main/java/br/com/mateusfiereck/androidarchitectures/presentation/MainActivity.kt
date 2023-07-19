package br.com.mateusfiereck.androidarchitectures.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mateusfiereck.androidarchitectures.databinding.MainActivityBinding
import br.com.mateusfiereck.androidarchitectures.presentation.mvi.MviActivity
import br.com.mateusfiereck.androidarchitectures.presentation.mvp.MvpActivity
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm.MvvmActivity
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm_statemachine.MvvmStateMachineActivity
import br.com.mateusfiereck.androidarchitectures.presentation.mvvm_viewstate.MvvmViewStateActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.buttonMvp.setOnClickListener {
            startActivity(Intent(this, MvpActivity::class.java))
        }

        binding.buttonMvvm.setOnClickListener {
            startActivity(Intent(this, MvvmActivity::class.java))
        }

        binding.buttonMvvmStatemachine.setOnClickListener {
            startActivity(Intent(this, MvvmStateMachineActivity::class.java))
        }

        binding.buttonMvvmViewstate.setOnClickListener {
            startActivity(Intent(this, MvvmViewStateActivity::class.java))
        }

        binding.buttonMvi.setOnClickListener {
            startActivity(Intent(this, MviActivity::class.java))
        }
    }
}