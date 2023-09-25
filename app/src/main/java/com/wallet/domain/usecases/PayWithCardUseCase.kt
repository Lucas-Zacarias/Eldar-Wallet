package com.wallet.domain.usecases

import com.wallet.domain.models.CardInput
import com.wallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class PayWithCardUseCase @Inject constructor(
    private val roomDatabaseService: RoomDatabaseService
) {

    suspend fun getCardData(cardNumber: ByteArray): CardInput {
        val cardData = roomDatabaseService.getCardData(cardNumber)

        return cardData.mapToCardInput()
    }

}