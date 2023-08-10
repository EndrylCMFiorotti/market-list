package com.study.marketlist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.study.marketlist.R
import com.study.marketlist.databinding.ActivityMainBinding
import com.study.marketlist.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setupFieldEmail()
        setupFieldPassword()
        setupButtonLogin()
    }

    private fun setupObserver() {
        with(viewModel) {
            setErrorEmail.observe(this@MainActivity) {
                binding.tilEmail.error = it
            }
            setStatusButtonLogin.observe(this@MainActivity) {
                binding.btnLogin.isEnabled = it
            }
            setLoginError.observe(this@MainActivity) {
                showSnackBarMessage(it)
            }
            setLoginSuccess.observe(this@MainActivity) {
                showSnackBarMessage(
                    getString(R.string.frag_login_message_success, it.name)
                )
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupFieldEmail() {
        binding.tietEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.changeEmail(text.toString())
        }
    }

    private fun setupFieldPassword() {
        binding.tietPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.changePassword(text.toString())
        }
    }

    private fun setupButtonLogin() {
        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.validationLogin(
                    email = tietEmail.text.toString(),
                    password = tietPassword.text.toString()
                )
            }
        }
    }
}