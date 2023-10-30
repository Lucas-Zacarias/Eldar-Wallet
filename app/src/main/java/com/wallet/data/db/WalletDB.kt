package com.wallet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wallet.data.db.dao.CardDAO
import com.wallet.data.db.dao.UserDAO
import com.wallet.data.db.tables.CardTable
import com.wallet.data.db.tables.UserTable


@Database(
    version = 1,
    entities = [UserTable::class, CardTable::class]
)


abstract class WalletDB : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    abstract fun cardDAO(): CardDAO

    companion object {
        const val DATABASE_NAME = "wallet_db"
    }
}
