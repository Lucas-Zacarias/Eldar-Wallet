package com.wallet.di

import com.wallet.data.api.QRGenerateClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_RAPID_API_QR_GENERATOR_URL = "https://neutrinoapi-qr-code.p.rapidapi.com/"

    @Singleton
    @Provides
    @Named("qr_generator_retrofit")
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_RAPID_API_QR_GENERATOR_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideQRGenerateClient(@Named("qr_generator_retrofit")retrofit: Retrofit): QRGenerateClient {
        return retrofit.create(QRGenerateClient::class.java)
    }
}