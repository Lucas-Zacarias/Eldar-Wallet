package com.wallet.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wallet.data.db.tables.CardTable

@Dao
interface CardDAO {

    @Insert
    suspend fun insertNewCard(cardTable: CardTable)

    @Query("SELECT * FROM CARD WHERE userId = :userId")
    suspend fun getAllUserCardsByItsId(userId: Int): List<CardTable>?

    @Query("SELECT EXISTS (SELECT * FROM CARD WHERE userId = :userId AND cardNumber = :cardNumber)")
    suspend fun verifyIfCardHasBeenAlreadyAdded(userId: Int, cardNumber: ByteArray): Boolean

    @Query("SELECT EXISTS (SELECT * FROM CARD WHERE cardNumber = :cardNumber)")
    suspend fun verifyCardIfFromOtherOwner(cardNumber:ByteArray): Boolean

    @Query("SELECT * FROM CARD WHERE cardNumber = :cardNumber")
    suspend fun getCardData(cardNumber: ByteArray): CardTable

}