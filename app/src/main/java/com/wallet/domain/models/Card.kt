package com.wallet.domain.models

import com.wallet.domain.usecases.EncryptionUseCase

data class Card(
    val name: String,
    val surname: String,
    val number: ByteArray,
    val month: ByteArray,
    val year: ByteArray,
    val cvc: ByteArray,
    val type: ByteArray
){
    fun mapToCardInput(): CardInput {
        return CardInput(
            name,
            surname,
            formatCardNumber(EncryptionUseCase.decrypt(number)),
            EncryptionUseCase.decrypt(month),
            EncryptionUseCase.decrypt(year),
            EncryptionUseCase.decrypt(cvc),
            getCardInputFromDecrypt(EncryptionUseCase.decrypt(type))
        )
    }

    private fun getCardInputFromDecrypt(type: String): CardType {
        return when(type) {
            "VISA" -> CardType.VISA

            "AMERICAN_EXPRESS" -> CardType.AMERICAN_EXPRESS

            "MASTERCARD" -> CardType.MASTERCARD

            else -> CardType.NOT_DEFINED
        }
    }

    private fun formatCardNumber(cardNumber: String): String {
        return if (cardNumber.length % 4 == 0)
            cardNumber.chunked(4).joinToString(" ")
        else cardNumber.take(4)
            .plus(" ")
            .plus(cardNumber.substring(4, 10))
            .plus(" ")
            .plus(cardNumber.takeLast(5))
    }
}
