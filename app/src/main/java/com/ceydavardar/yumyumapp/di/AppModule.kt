package com.ceydavardar.yumyumapp.di

import android.content.Context
import androidx.room.Room
import com.ceydavardar.yumyumapp.data.datasource.FoodsDataSource
import com.ceydavardar.yumyumapp.data.datasource.UserDataSource
import com.ceydavardar.yumyumapp.data.repo.FoodsRepository
import com.ceydavardar.yumyumapp.data.repo.UserRepository
import com.ceydavardar.yumyumapp.retrofit.APIUtils
import com.ceydavardar.yumyumapp.retrofit.FoodsDao
import com.ceydavardar.yumyumapp.room.Database
import com.ceydavardar.yumyumapp.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodsRepository(fds: FoodsDataSource): FoodsRepository {
        return FoodsRepository(fds)
    }

    @Provides
    @Singleton
    fun provideFoodsDataSource(fdao: FoodsDao): FoodsDataSource {
        return FoodsDataSource(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodsDao(): FoodsDao {
        return APIUtils.getFoodsDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext context: Context): UserDao {
        val db = Room.databaseBuilder(context, Database::class.java, "users.sqlite")
            .createFromAsset("users.sqlite").build()
        return db.getUserDao()
    }

    @Provides
    @Singleton
    fun getUserDataSource(userDao: UserDao): UserDataSource {
        return UserDataSource(userDao)
    }

    @Provides
    @Singleton
    fun getUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepository(userDataSource)
    }

}