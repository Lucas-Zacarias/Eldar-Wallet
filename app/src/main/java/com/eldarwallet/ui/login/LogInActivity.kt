package com.eldarwallet.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eldarwallet.databinding.ActivityLogInBinding
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserLogIn
import com.eldarwallet.ui.home.HomeActivity
import com.eldarwallet.ui.signup.SignUpActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnLogIn: MaterialButton
    private lateinit var btnSignUp: MaterialButton
    private val logInViewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        getViews()
        setListeners()
        setObserver()
    }

    private fun getViews() {
        email = binding.etEmail.editText!!
        password = binding.etPassword.editText!!
        btnLogIn = binding.mbLogIn
        btnSignUp = binding.mbSignUp
    }

    private fun setListeners() {
        logIn()
        goToSignUpActivity()
    }

    private fun logIn() {
        btnLogIn.setOnClickListener {
            logInViewModel.validateLogIn(
                UserLogIn(
                    email.text.toString(),
                    password.text.toString()
                )
            )
        }
    }

    private fun goToSignUpActivity() {
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun setObserver() {
        logInViewModel.logInState.observe(this) { logInResult ->
            when(logInResult) {
                LoginResult.Success -> goToHome()

                LoginResult.EmptyFields -> setAlertLogInProblem("Completar todos los campos")

                LoginResult.EmailInvalid -> setAlertLogInProblem("El formato del email, ej: mario.paredes@gmail.com")

                LoginResult.UserNotFound -> setAlertLogInProblem("Tus credenciales")

                else -> {}
            }
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun setAlertLogInProblem(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("No pudimos acceder a tu cuenta")
            .setMessage("Revisá: $message")
            .setPositiveButton("OK"){dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }
}