package com.eldarwallet.domain.services

import com.eldarwallet.domain.models.Card
import com.eldarwallet.domain.models.NewCardResult

interface RoomDatabaseService {
    suspend fun getUserData(email: String): Pair<String, String>

    suspend fun getUserId(email: String): Int

    suspend fun addNewCard(userId: Int, card: Card): NewCardResult

    suspend fun getCardData(cardNumber: ByteArray): Card

    suspend fun getAllUserCards(userId: Int): List<Card>
}