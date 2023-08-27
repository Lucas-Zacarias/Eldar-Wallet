package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val roomDatabaseService: RoomDatabaseService
) {

    private val editor = sharedPreferences.edit()

    suspend fun getCardList(): List<Card> {
        return roomDatabaseService.getAllUserCards(getUserId())
    }

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

    private suspend fun getUserId(): Int {
        val userEmail = sharedPreferences.getString("email", null)

        userEmail?.let{
            return roomDatabaseService.getUserId(userEmail)
        }

        return 0
    }

}