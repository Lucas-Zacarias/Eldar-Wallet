package com.eldarwallet.domain.services

import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.User

interface AuthenticationService {

    suspend fun createAccount(user: User): LoginResult

    suspend fun login(email: String, password: ByteArray): Pair<LoginResult, Pair<String?, String?>>

}