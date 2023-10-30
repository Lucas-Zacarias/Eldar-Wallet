package com.wallet.di

import android.content.Context
import androidx.room.Room
import com.wallet.data.db.WalletDB
import com.wallet.data.db.dao.CardDAO
import com.wallet.data.db.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideWalletDB(@ApplicationContext context: Context): WalletDB {
        return Room.databaseBuilder(
            context,
            WalletDB::class.java,
            WalletDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDAO(walletDB: WalletDB) : UserDAO {
        return walletDB.userDAO()
    }

    @Provides
    fun provideCardDAO(walletDB: WalletDB): CardDAO {
        return walletDB.cardDAO()
    }
}