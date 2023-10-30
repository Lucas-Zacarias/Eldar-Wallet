package com.wallet.domain.models

import com.wallet.domain.usecases.EncryptionUseCase

data class UserLogIn(
    val email: String,
    val password: String
){
    fun encryptPassword(): ByteArray {
        return EncryptionUseCase.encrypt(password)
    }
}