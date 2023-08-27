package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val editor = sharedPreferences.edit()

    fun getUserInitials(): String {
        val name = getUserName()
        val surname = getUserSurname()

        return if (name != null && surname != null) {
            (name.first().plus(surname.first().toString())).uppercase()
        } else {
            "EW"
        }
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("name", null)
    }

    fun logOut() {
        editor.clear().apply()
    }

    private fun getUserSurname(): String? {
        return sharedPreferences.getString("surname", null)
    }

}