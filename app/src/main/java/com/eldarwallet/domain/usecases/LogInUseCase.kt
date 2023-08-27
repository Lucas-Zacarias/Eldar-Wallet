package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import androidx.core.util.PatternsCompat
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.UserLogIn
import com.eldarwallet.domain.services.AuthenticationService
import com.eldarwallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    sharedPreferences: SharedPreferences,
    private val authenticationService: AuthenticationService,
    private val roomDatabaseService: RoomDatabaseService
) {
    private val editor = sharedPreferences.edit()

    suspend fun logIn(userLogIn: UserLogIn): LoginResult {
        if (!validateFields(userLogIn)) return LoginResult.EmptyFields

        if (!validateEmailFormat(userLogIn.email)) return LoginResult.EmailInvalid

        return authenticationService.login(userLogIn.email, userLogIn.encryptPassword())
    }

    suspend fun saveUserSession(email: String) {
        val userData = roomDatabaseService.getUserData(email)
        editor.putString("email", email).apply()
        editor.putString("name", userData.first).apply()
        editor.putString("surname", userData.second).apply()
    }

    private fun validateFields(userLogIn: UserLogIn): Boolean {
        return !(userLogIn.email.isEmpty() ||
                userLogIn.password.isEmpty())
    }

    private fun validateEmailFormat(email: String): Boolean =
        PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

}