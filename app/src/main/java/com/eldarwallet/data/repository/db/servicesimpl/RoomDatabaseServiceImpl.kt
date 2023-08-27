package com.eldarwallet.data.repository.db.servicesimpl

import com.eldarwallet.data.repository.db.UserDAO
import com.eldarwallet.domain.services.RoomDatabaseService
import javax.inject.Inject

class RoomDatabaseServiceImpl @Inject constructor(
    private val userDAO: UserDAO
): RoomDatabaseService {
    override suspend fun getUserData(email: String): Pair<String, String> {
        val userData = userDAO.getUserData(email)
        val name = userData.name
        val surname = userData.surname

        return Pair(name, surname)
    }
}