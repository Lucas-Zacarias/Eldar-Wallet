package com.wallet.domain.services

import com.wallet.domain.models.Card
import com.wallet.domain.models.NewCardResult

interface RoomDatabaseService {
    suspend fun getUserData(email: String): Pair<String, String>

    suspend fun getUserId(email: String): Int

    suspend fun addNewCard(userId: Int, card: Card): NewCardResult

    suspend fun getCardData(cardNumber: ByteArray): Card

    suspend fun getAllUserCards(userId: Int): List<Card>
}