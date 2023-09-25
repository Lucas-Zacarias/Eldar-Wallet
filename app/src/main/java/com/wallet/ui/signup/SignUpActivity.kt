package com.wallet.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wallet.ui.home.HomeActivity
import com.wallet.ui.theme.WalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WalletTheme {
                SignUpScreen(
                    goToLoginEvent = {
                        goToLogInActivity()
                    },
                    goToHomeEvent = {
                        goToHome()
                    },
                    viewModel = signUpViewModel
                )
            }
        }

    }

    private fun goToLogInActivity() {
        finish()
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

}