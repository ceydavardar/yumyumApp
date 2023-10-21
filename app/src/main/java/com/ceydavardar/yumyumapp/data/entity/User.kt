package com.ceydavardar.yumyumapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") var id: Int,
    @ColumnInfo(name = "user_username") var username: String,
    @ColumnInfo(name = "user_password") var password: String
): Serializable {}