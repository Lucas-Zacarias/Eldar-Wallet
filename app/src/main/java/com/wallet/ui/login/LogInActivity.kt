package com.wallet.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wallet.ui.home.HomeActivity
import com.wallet.ui.signup.SignUpActivity
import com.wallet.ui.theme.WalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val logInViewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WalletTheme {
                LoginScreen(
                    signUpEvent = {
                        goToSignUpActivity()
                    },
                    goToHomeEvent = {
                        goToHome()
                    },
                    viewModel = logInViewModel
                )
            }
        }

    }

    private fun goToSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}