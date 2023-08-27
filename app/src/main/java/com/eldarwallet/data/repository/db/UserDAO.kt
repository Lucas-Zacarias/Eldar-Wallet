package com.eldarwallet.data.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert
    suspend fun insertNewUser(userTable: UserTable)

    @Query("SELECT * FROM USER WHERE email = :email AND password = :password")
    suspend fun getUserData(email: String, password: ByteArray): UserTable?

    @Query("SELECT EXISTS (SELECT * FROM USER WHERE email = :email)")
    suspend fun verifyAccountExistence(email: String): Boolean
}