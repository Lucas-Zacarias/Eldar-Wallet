package com.wallet.domain.services

import com.wallet.domain.models.LoginResult
import com.wallet.domain.models.User

interface AuthenticationService {

    suspend fun createAccount(user: User): LoginResult

    suspend fun login(email: String, password: ByteArray): LoginResult

}