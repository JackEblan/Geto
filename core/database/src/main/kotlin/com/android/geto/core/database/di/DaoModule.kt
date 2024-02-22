package com.android.geto.core.database.di

import com.android.geto.core.database.AppDatabase
import com.android.geto.core.database.dao.AppSettingsDao
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
    fun appSettingsDao(appDatabase: AppDatabase): AppSettingsDao =
        appDatabase.appSettingsDao
}