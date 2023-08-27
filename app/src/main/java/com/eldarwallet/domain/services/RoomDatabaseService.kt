package com.eldarwallet.domain.services

interface RoomDatabaseService {
    suspend fun getUserData(email: String): Pair<String, String>

}