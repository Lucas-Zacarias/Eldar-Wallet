package com.wallet.data.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CARD")
data class CardTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardId")
    val cardId: Int = 0,

    @ColumnInfo(name = "userId")
    val userId: Int,

    @ColumnInfo(name = "ownerName")
    val ownerName: String,

    @ColumnInfo(name = "ownerSurname")
    val ownerSurname: String,

    @ColumnInfo(name = "cardNumber")
    val cardNumber: ByteArray,

    @ColumnInfo(name = "cardExpirationMonth")
    val cardExpirationMonth: ByteArray,

    @ColumnInfo(name = "cardExpirationYear")
    val cardExpirationYear: ByteArray,

    @ColumnInfo(name = "cardCVC")
    val cardCVC: ByteArray,

    @ColumnInfo(name = "cardType")
    val cardType: ByteArray
)