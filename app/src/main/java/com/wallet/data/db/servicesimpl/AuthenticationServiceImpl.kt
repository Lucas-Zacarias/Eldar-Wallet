package com.wallet.data.db.servicesimpl

import com.wallet.data.db.dao.UserDAO
import com.wallet.data.db.tables.UserTable
import com.wallet.domain.models.LoginResult
import com.wallet.domain.models.User
import com.wallet.domain.services.AuthenticationService
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