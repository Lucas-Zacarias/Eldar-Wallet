package com.eldarwallet.di

import com.eldarwallet.data.repository.db.UserDAO
import com.eldarwallet.data.repository.db.servicesimpl.AuthenticationServiceImpl
import com.eldarwallet.data.repository.db.servicesimpl.RoomDatabaseServiceImpl
import com.eldarwallet.domain.services.AuthenticationService
import com.eldarwallet.domain.services.RoomDatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InterfaceModule {

    @Provides
    fun getAuthenticationService(
        userDAO: UserDAO
    ): AuthenticationService {
        return AuthenticationServiceImpl(userDAO)
    }

    @Provides
    fun getRoomDatabaseService(
        userDAO: UserDAO
    ): RoomDatabaseService {
        return RoomDatabaseServiceImpl(userDAO)
    }
}