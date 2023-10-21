package com.ceydavardar.yumyumapp.data.repo

import com.ceydavardar.yumyumapp.data.datasource.UserDataSource
import com.ceydavardar.yumyumapp.data.entity.User

class UserRepository(var dataSource: UserDataSource) {

    var username: String? = null

    suspend fun save(username: String, password: String) {
        dataSource.save(username, password)
    }

    suspend fun getPassword(username: String): String? {
        return dataSource.getPassword(username)
    }

    suspend fun getUsers(): List<User> {
        return dataSource.getUsers()
    }

}