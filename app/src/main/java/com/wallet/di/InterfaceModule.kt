package com.wallet.di

import com.wallet.data.api.QRGenerateGateway
import com.wallet.data.api.serviceImpl.ApiServiceImpl
import com.wallet.data.db.dao.CardDAO
import com.wallet.data.db.dao.UserDAO
import com.wallet.data.db.servicesimpl.AuthenticationServiceImpl
import com.wallet.data.db.servicesimpl.RoomDatabaseServiceImpl
import com.wallet.domain.services.ApiService
import com.wallet.domain.services.AuthenticationService
import com.wallet.domain.services.RoomDatabaseService
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
        userDAO: UserDAO,
        cardDAO: CardDAO
    ): RoomDatabaseService {
        return RoomDatabaseServiceImpl(userDAO, cardDAO)
    }

    @Provides
    fun getApiService(
        qrGenerateGateway: QRGenerateGateway
    ): ApiService {
        return ApiServiceImpl(qrGenerateGateway)
    }
}