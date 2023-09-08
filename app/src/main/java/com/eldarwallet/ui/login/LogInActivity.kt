package com.eldarwallet.ui.login

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eldarwallet.ui.home.HomeActivity
import com.eldarwallet.ui.screens.LoginScreen
import com.eldarwallet.ui.signup.SignUpActivity
import com.eldarwallet.ui.theme.EldarWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val logInViewModel: LogInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EldarWalletTheme {
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

    @Preview(
        showBackground = true,
        name = "Light mode",
        uiMode = UI_MODE_NIGHT_NO
    )
    @Preview(
        showBackground = true,
        name = "Night mode",
        uiMode = UI_MODE_NIGHT_YES
    )
    @Composable
    private fun Preview() {
        EldarWalletTheme {
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