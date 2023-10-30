package com.wallet.domain.usecases

import android.content.SharedPreferences
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun checkCurrentSession(): Boolean =
        sharedPreferences.contains("name") && sharedPreferences.contains("surname")

}