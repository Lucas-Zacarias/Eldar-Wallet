package com.wallet.domain.models

import com.wallet.domain.usecases.EncryptionUseCase

data class CardInput(
    val name: String,
    val surname: String,
    val number: String,
    val month: String,
    val year: String,
    val cvc: String,
    val type: CardType
){
    fun mapToCard(): Card {
        return Card(
            name = name,
            surname = surname,
            number = EncryptionUseCase.encrypt(number),
            month = EncryptionUseCase.encrypt(month),
            year = EncryptionUseCase.encrypt(year),
            cvc = EncryptionUseCase.encrypt(cvc),
            type = EncryptionUseCase.encrypt(type.toString())
        )
    }
}
