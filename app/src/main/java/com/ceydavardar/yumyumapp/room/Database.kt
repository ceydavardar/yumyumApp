package com.ceydavardar.yumyumapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ceydavardar.yumyumapp.data.entity.User

@Database(entities = [User::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun getUserDao(): UserDao

}