package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import androidx.core.util.PatternsCompat
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserLogIn
import com.eldarwallet.domain.services.AuthenticationService
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val authenticationService: AuthenticationService
) {
    private val editor = sharedPreferences.edit()

    suspend fun logIn(userLogIn: UserLogIn): LoginResult {
        if (!validateFields(userLogIn)) return LoginResult.EmptyFields

        if (!validateEmailFormat(userLogIn.email)) return LoginResult.EmailInvalid

        val loginResult = authenticationService.login(userLogIn.email, userLogIn.encryptPassword())

        if (loginResult.second.first != null && loginResult.second.second != null) {
            saveUserSession(
                loginResult.second.first.toString(),
                loginResult.second.second.toString()
            )
        }

        return loginResult.first
    }

    private fun saveUserSession(name: String, surname: String) {
        editor.putString("name", name).apply()
        editor.putString("surname", surname).apply()
    }

    private fun validateFields(userLogIn: UserLogIn): Boolean {
        return !(userLogIn.email.isEmpty() ||
                userLogIn.password.isEmpty())
    }

    private fun validateEmailFormat(email: String): Boolean =
        PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

}