package com.ceydavardar.yumyumapp.data.datasource

import com.ceydavardar.yumyumapp.data.entity.User
import com.ceydavardar.yumyumapp.room.UserDao

class UserDataSource(val userDao: UserDao) {

    suspend fun save(username: String, password: String) {
        userDao.save(User(0,username,password))
    }

    suspend fun getPassword(username: String): String? {
        return userDao.getPassword(username)
    }

    suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

}