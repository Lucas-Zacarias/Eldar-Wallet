package com.eldarwallet.domain.usecases

import android.content.SharedPreferences
import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.models.CardInput
import com.eldarwallet.domain.models.CardType
import com.eldarwallet.domain.models.NewCardResult
import com.eldarwallet.domain.services.RoomDatabaseService
import java.time.LocalDate
import javax.inject.Inject

class AddNewCardUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val roomDatabaseService: RoomDatabaseService
) {

    suspend fun addNewCard(cardInput: CardInput): NewCardResult {
        if (!validateFields(cardInput)) return NewCardResult.EmptyFields

        if (!validateMonth(cardInput.month)) return NewCardResult.MonthInvalid

        if (!validateYear(cardInput.year)) return NewCardResult.YearInvalid

        if (!validateNumberLength(cardInput.number, cardInput.type)) return NewCardResult.NumberInvalid

        if (!validateCVCLength(cardInput.cvc, cardInput.type)) return NewCardResult.CVCLengthInvalid

        if (!validateCardNameOwner(cardInput.name, cardInput.surname)) return NewCardResult.OwnerNameInvalid

        if (!validateCardType(cardInput.number)) return NewCardResult.CardTypeInvalid

        return roomDatabaseService.addNewCard(getUserId(), cardInput.mapToCard())
    }

    fun setCardType(cardNumber: String): CardType {
        var cardType = CardType.NOT_DEFINED

        if (cardNumber.length == 15 && cardNumber.first().toString() == "3") {
            cardType = CardType.AMERICAN_EXPRESS
        } else {
            if (cardNumber.length == 16 && cardNumber.first().toString() == "4") {
                cardType = CardType.VISA
            } else {
                if (cardNumber.length == 16 && cardNumber.first().toString() == "5")
                    cardType = CardType.MASTERCARD
            }
        }

        return cardType
    }

    private fun validateFields(cardInput: CardInput): Boolean {
        return !(cardInput.name.isEmpty() ||
                cardInput.surname.isEmpty() ||
                cardInput.number.isEmpty() ||
                cardInput.month.isEmpty() ||
                cardInput.year.isEmpty() ||
                cardInput.cvc.isEmpty() ||
                cardInput.type.toString().isEmpty())
    }

    private fun validateMonth(monthExpiration: String): Boolean {
        val today = LocalDate.now()
        val month = today.monthValue

        return monthExpiration.toInt() in month..12
    }

    private fun validateYear(yearExpiration: String): Boolean {
        val today = LocalDate.now()
        val year = today.year

        return yearExpiration.toInt() in year..2099
    }

    private fun validateNumberLength(cardLength: String, cardType: CardType): Boolean {
        return if (cardType == CardType.AMERICAN_EXPRESS && cardLength.length == 15) {
            true
        } else cardType == CardType.VISA || cardType == CardType.MASTERCARD && cardLength.length == 16
    }

    private fun validateCVCLength(cvcLength: String, cardType: CardType): Boolean {
        return if (cardType == CardType.AMERICAN_EXPRESS && cvcLength.length == 4) {
            true
        } else (cardType == CardType.VISA || cardType == CardType.MASTERCARD) && cvcLength.length == 3
    }

    private fun validateCardNameOwner(nameInput: String, surnameInput: String): Boolean {
        val nameCurrentSession = getName()
        val surnameCurrentSession = getSurname()

        return (nameCurrentSession != null &&
                nameCurrentSession == nameInput &&
                surnameCurrentSession != null &&
                surnameCurrentSession == surnameInput)
    }

    private fun getName(): String? {
        return sharedPreferences.getString("name", null)
    }

    private fun getSurname(): String? {
        return sharedPreferences.getString("surname", null)
    }

    private fun validateCardType(cardNumber: String): Boolean {
        return (cardNumber.length in 15..16 &&
                cardNumber.first().toString() in listOf("3", "4", "5"))
    }

    private suspend fun getUserId(): Int {
        val userEmail = sharedPreferences.getString("email", null)

        userEmail?.let{
            return roomDatabaseService.getUserId(userEmail)
        }

        return 0
    }
}