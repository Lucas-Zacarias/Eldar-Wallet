package com.eldarwallet.domain.services

import java.io.InputStream

interface ApiService {

    suspend fun getGeneratedQR(content: String): InputStream?

}