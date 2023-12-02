package com.core.database.di

import com.core.database.AppDatabase
import com.core.database.dao.UserAppSettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun userAppSettingsDao(appDatabase: AppDatabase): UserAppSettingsDao =
        appDatabase.userAppSettingsDao
}