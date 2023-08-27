package com.eldarwallet.data.repository.db.servicesimpl

import com.eldarwallet.data.repository.db.UserDAO
import com.eldarwallet.data.repository.db.UserTable
import com.eldarwallet.domain.models.LoginResult
import com.eldarwallet.domain.models.User
import com.eldarwallet.domain.services.AuthenticationService
import javax.inject.Inject

class AuthenticationServiceImpl @Inject constructor(
    private val userDAO: UserDAO
): AuthenticationService {
    override suspend fun createAccount(user: User): LoginResult {
        return if(userDAO.verifyAccountExistence(user.email)) {
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

    override suspend fun login(email: String, password: ByteArray): Pair<LoginResult, Pair<String?, String?>> {
        val userData = userDAO.getUserData(email, password)

        return if(userData != null) {
            Pair(LoginResult.Success, Pair(userData.name, userData.surname))
        }else{
            Pair(LoginResult.UserNotFound, Pair(null, null))
        }
    }
}