package com.eldarwallet.data.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eldarwallet.data.repository.db.dao.CardDAO
import com.eldarwallet.data.repository.db.dao.UserDAO
import com.eldarwallet.data.repository.db.tables.CardTable
import com.eldarwallet.data.repository.db.tables.UserTable


@Database(
    version = 1,
    entities = [UserTable::class, CardTable::class]
)


abstract class EldarWalletDB : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    abstract fun cardDAO(): CardDAO

    companion object {
        const val DATABASE_NAME = "eldar_wallet_db"
    }
}
