package com.wallet.domain.models

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val password: ByteArray
)
