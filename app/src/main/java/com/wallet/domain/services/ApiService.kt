package com.wallet.domain.services

import java.io.InputStream

interface ApiService {

    suspend fun getGeneratedQR(content: String): InputStream?

}