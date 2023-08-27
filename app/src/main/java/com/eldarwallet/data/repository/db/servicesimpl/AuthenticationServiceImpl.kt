package com.eldarwallet.data.repository.db.servicesimpl

import com.eldarwallet.data.repository.db.dao.UserDAO
import com.eldarwallet.data.repository.db.tables.UserTable
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.User
import com.eldarwallet.domain.services.AuthenticationService
import javax.inject.Inject

class AuthenticationServiceImpl @Inject constructor(
    private val userDAO: UserDAO
): AuthenticationService {
    override suspend fun createAccount(user: User): LoginResult {
        return if(userDAO.verifyAccountExistenceByEmail(user.email)) {
            LoginResult.ExistingUser
        }else{
            userDAO.insertNewUser(
                UserTable(
                    name = user.name,
                    surname = user.surname,
                    email = user.email,
                    password = user.password
                )
            )
            LoginResult.Success
        }
    }

    override suspend fun login(email: String, password: ByteArray): LoginResult {
        return if(userDAO.verifyAccountExistenceByEmailAndPassword(email, password)) {
            LoginResult.Success
        } else {
            LoginResult.UserNotFound
        }
    }
}