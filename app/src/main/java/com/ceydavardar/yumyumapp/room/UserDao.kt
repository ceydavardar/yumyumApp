package com.ceydavardar.yumyumapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ceydavardar.yumyumapp.data.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun save(user: User)

    @Query("SELECT user_password FROM users WHERE user_username = :username")
    suspend fun getPassword(username: String): String?

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

}