package com.wallet.domain.usecases

import android.content.SharedPreferences
import com.wallet.domain.models.Card
import com.wallet.domain.models.CardType
import com.wallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val roomDatabaseService: RoomDatabaseService
) {

    private val editor = sharedPreferences.edit()

    suspend fun getCardList(): List<Card> {
        return sortCardListByType(roomDatabaseService.getAllUserCards(getUserId()))
    }

    fun getUserInitials(): String {
        val name = getUserName()
        val surname = getUserSurname()

        return if (name != null && surname != null) {
            (name.first().plus(surname.first().toString())).uppercase()
        } else {
            "WL"
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

    private fun sortCardListByType(cards: List<Card>): List<Card> {
        return cards.sortedWith(
            compareBy{ card ->
                getCardTypePriority(card.type)
            }
        )
    }

    private fun getCardTypePriority(cardType: ByteArray): Int {

        return when(EncryptionUseCase.decrypt(cardType)) {
            CardType.VISA.toString() -> 1

            CardType.MASTERCARD.toString() -> 2

            CardType.AMERICAN_EXPRESS.toString() -> 3

            else -> 4
         }

    }
}