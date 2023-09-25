package com.wallet.domain.models

sealed class LoginResult {
    object Error: LoginResult()
    object Success: LoginResult()
    object DistinctEmail: LoginResult()
    object EmailInvalid: LoginResult()
    object PasswordInvalid: LoginResult()
    object DistinctPassword: LoginResult()
    object EmptyFields: LoginResult()
    object ExistingUser: LoginResult()
    object UserNotFound: LoginResult()
}

