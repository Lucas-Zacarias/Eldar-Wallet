package com.eldarwallet.data.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eldarwallet.data.repository.db.tables.UserTable

@Dao
interface UserDAO {

    @Insert
    suspend fun insertNewUser(userTable: UserTable)

    @Query("SELECT * FROM USER WHERE email = :email")
    suspend fun getUserData(email: String): UserTable

    @Query("SELECT EXISTS (SELECT * FROM USER WHERE email = :email)")
    suspend fun verifyAccountExistenceByEmail(email: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM USER WHERE email = :email AND password = :password)")
    suspend fun verifyAccountExistenceByEmailAndPassword(email: String, password: ByteArray): Boolean

    @Query("SELECT USER.id FROM USER WHERE email = :email")
    suspend fun getUserIdByEmail(email: String): Int
}