package com.eldarwallet.data.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    version = 1,
    entities = [UserTable::class]
)


abstract class EldarWalletDB : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        const val DATABASE_NAME = "eldar_wallet_db"
    }
}
