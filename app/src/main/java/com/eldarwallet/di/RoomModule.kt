package com.eldarwallet.di

import android.content.Context
import androidx.room.Room
import com.eldarwallet.data.repository.db.EldarWalletDB
import com.eldarwallet.data.repository.db.dao.CardDAO
import com.eldarwallet.data.repository.db.dao.UserDAO
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
    fun provideEldarWalletDB(@ApplicationContext context: Context): EldarWalletDB {
        return Room.databaseBuilder(
            context,
            EldarWalletDB::class.java,
            EldarWalletDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideUserDAO(eldarWalletDB: EldarWalletDB) : UserDAO {
        return eldarWalletDB.userDAO()
    }

    @Provides
    fun provideCardDAO(eldarWalletDB: EldarWalletDB): CardDAO {
        return eldarWalletDB.cardDAO()
    }
}