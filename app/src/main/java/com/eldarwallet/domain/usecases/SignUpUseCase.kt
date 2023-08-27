package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import androidx.core.util.PatternsCompat
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserSignUp
import com.eldarwallet.domain.services.AuthenticationService
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    sharedPrefs: SharedPreferences,
    private val authenticationService: AuthenticationService
) {

    private val editor = sharedPrefs.edit()

    suspend fun signUp(userSignUp: UserSignUp): LoginResult {
        if(!validateFields(userSignUp)) return LoginResult.EmptyFields

        if(!validateEmailFormat(userSignUp.email)) return LoginResult.EmailInvalid

        if(!validatePasswordLength(userSignUp.password)) return LoginResult.PasswordInvalid

        if(userSignUp.email != userSignUp.emailConfirm) return LoginResult.DistinctEmail

        if(userSignUp.password != userSignUp.passwordConfirm) return LoginResult.DistinctPassword

        return authenticationService.createAccount(userSignUp.mapToUser())
    }

    fun saveUserSession(userSignUp: UserSignUp) {
        editor.putString("name", userSignUp.name).apply()
        editor.putString("surname", userSignUp.surname).apply()
    }

    private fun validateFields(userSignUp: UserSignUp): Boolean {
        return !(userSignUp.name.isEmpty() ||
                userSignUp.surname.isEmpty() ||
                userSignUp.email.isEmpty() ||
                userSignUp.emailConfirm.isEmpty() ||
                userSignUp.password.isEmpty() ||
                userSignUp.passwordConfirm.isEmpty())
    }

    private fun validateEmailFormat(email: String): Boolean =
        PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

    private fun validatePasswordLength(password: String): Boolean = password.length >= PASSWORD_MIN_LENGTH

    companion object {
        private const val PASSWORD_MIN_LENGTH = 6
    }
}