package com.eldarwallet.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.eldarwallet.ui.home.HomeActivity
import com.eldarwallet.ui.theme.EldarWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EldarWalletTheme {
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