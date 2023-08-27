package com.eldarwallet.domain.models

data class Card(
    val name: String,
    val surname: String,
    val number: ByteArray,
    val month: ByteArray,
    val year: ByteArray,
    val cvc: ByteArray,
    val type: ByteArray
)
