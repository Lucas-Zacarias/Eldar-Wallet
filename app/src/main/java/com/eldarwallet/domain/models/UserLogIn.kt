package com.eldarwallet.domain.models

import com.eldarwallet.domain.usecases.EncryptionUseCase

data class UserLogIn(
    val email: String,
    val password: String
){
    fun encryptPassword(): ByteArray {
        return EncryptionUseCase.encrypt(password)
    }
}