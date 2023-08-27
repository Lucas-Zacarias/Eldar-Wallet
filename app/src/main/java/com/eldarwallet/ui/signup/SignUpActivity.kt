package com.eldarwallet.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eldarwallet.databinding.ActivitySignUpBinding
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserSignUp
import com.eldarwallet.ui.home.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var email: EditText
    private lateinit var emailConfirm: EditText
    private lateinit var password: EditText
    private lateinit var passwordConfirm: EditText
    private lateinit var btnSignUp: MaterialButton
    private lateinit var btnLogIn: MaterialButton
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        getViews()
        setListeners()
        setObserver()
    }

    private fun getViews() {
        name = binding.etName.editText!!
        surname = binding.etSurname.editText!!
        email = binding.etEmail.editText!!
        emailConfirm = binding.etConfirmEmail.editText!!
        password = binding.etPassword.editText!!
        passwordConfirm = binding.etConfirmPassword.editText!!
        btnSignUp = binding.mbSignUp
        btnLogIn = binding.mbLogIn
    }

    private fun setListeners() {
        createAccount()
        goToLogInActivity()
    }

    private fun createAccount() {
        btnSignUp.setOnClickListener {
            signUpViewModel.validateSignUpForm(
                UserSignUp(
                    name.text.toString(),
                    surname.text.toString(),
                    email.text.toString(),
                    emailConfirm.text.toString(),
                    password.text.toString(),
                    passwordConfirm.text.toString()
                )
            )
        }
    }

    private fun goToLogInActivity() {
        btnLogIn.setOnClickListener {
            finish()
        }
    }

    private fun setObserver() {
        signUpViewModel.signUpState.observe(this) { signUpResult ->
            when(signUpResult) {
                LoginResult.Success -> goToHome()

                LoginResult.EmptyFields -> setAlertSignUpProblem("Completar todos los campos")

                LoginResult.DistinctEmail -> setAlertSignUpProblem("Ver si los email coinciden")

                LoginResult.DistinctPassword -> setAlertSignUpProblem("Ver si las contraseñas coinciden")

                LoginResult.EmailInvalid -> setAlertSignUpProblem("El formato del email, ej: mario.paredes@gmail.com")

                LoginResult.PasswordInvalid -> setAlertSignUpProblem("El largo de la contraseña (debe ser de al menos 6 caracteres)")

                LoginResult.ExistingUser -> setAlertSignUpProblem("Probar con otro email")

                LoginResult.Error -> setAlertSignUpProblem("No pudimos detectar la causa")

                else -> {}
            }
        }
    }

    private fun setAlertSignUpProblem(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("No pudimos crear tu cuenta")
            .setMessage("Revisá: $message")
            .setPositiveButton("OK"){dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}