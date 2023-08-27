package com.eldarwallet.domain.usecases

import com.eldarwallet.domain.models.CardInput
import com.eldarwallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class PayWithCardUseCase @Inject constructor(
    private val roomDatabaseService: RoomDatabaseService
) {

    suspend fun getCardData(cardNumber: ByteArray): CardInput {
        val cardData = roomDatabaseService.getCardData(cardNumber)

        return cardData.mapToCardInput()
    }

}