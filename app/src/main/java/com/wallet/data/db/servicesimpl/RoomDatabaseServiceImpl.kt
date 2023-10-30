package com.wallet.data.db.servicesimpl

import com.wallet.data.db.dao.CardDAO
import com.wallet.data.db.dao.UserDAO
import com.wallet.data.db.tables.CardTable
import com.wallet.domain.models.Card
import com.wallet.domain.models.NewCardResult
import com.wallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class RoomDatabaseServiceImpl @Inject constructor(
    private val userDAO: UserDAO,
    private val cardDAO: CardDAO
) : RoomDatabaseService {
    override suspend fun getUserData(email: String): Pair<String, String> {
        val userData = userDAO.getUserData(email)
        val name = userData.name
        val surname = userData.surname

        return Pair(name, surname)
    }

    override suspend fun getUserId(email: String): Int {
        return userDAO.getUserIdByEmail(email)
    }

    override suspend fun addNewCard(userId: Int, card: Card): NewCardResult {
        return if (cardDAO.verifyIfCardHasBeenAlreadyAdded(userId, card.number)) {
            NewCardResult.CardAlreadyAdded
        } else if (cardDAO.verifyCardIfFromOtherOwner(card.number)) {
            NewCardResult.OwnerInvalid
        } else {
            cardDAO.insertNewCard(
                CardTable(
                    userId = userId,
                    ownerName = card.name,
                    ownerSurname = card.surname,
                    cardNumber = card.number,
                    cardExpirationMonth = card.month,
                    cardExpirationYear = card.year,
                    cardCVC = card.cvc,
                    cardType = card.type
                )
            )
            NewCardResult.Success
        }
    }

    override suspend fun getCardData(cardNumber: ByteArray): Card {
        val cardData = cardDAO.getCardData(cardNumber)

        return Card(
            cardData.ownerName,
            cardData.ownerSurname,
            cardData.cardNumber,
            cardData.cardExpirationMonth,
            cardData.cardExpirationYear,
            cardData.cardCVC,
            cardData.cardType
        )
    }

    override suspend fun getAllUserCards(userId: Int): List<Card> {
        val cards = cardDAO.getAllUserCardsByItsId(userId)

        return cards?.map {
            Card(
                it.ownerName,
                it.ownerSurname,
                it.cardNumber,
                it.cardExpirationMonth,
                it.cardExpirationMonth,
                it.cardCVC,
                it.cardType
            )
        }
            ?: emptyList()
    }
}