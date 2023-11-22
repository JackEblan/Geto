package com.core.data.di

import android.content.Context
import com.core.common.di.DefaultDispatcher
import com.core.common.di.IoDispatcher
import com.core.data.repository.SettingsRepositoryImpl
import com.core.data.repository.UserAppListRepositoryImpl
import com.core.data.repository.UserAppSettingsRepositoryImpl
import com.core.database.room.AppDatabase
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.UserAppListRepository
import com.core.domain.repository.UserAppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun userAppListRepository(
        @ApplicationContext context: Context,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): UserAppListRepository = UserAppListRepositoryImpl(
        defaultDispatcher = defaultDispatcher, packageManager = context.packageManager
    )

    @Singleton
    @Provides
    fun userAppSettingsRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher, appDatabase: AppDatabase
    ): UserAppSettingsRepository = UserAppSettingsRepositoryImpl(
        ioDispatcher = ioDispatcher, appDatabase = appDatabase
    )

    @Singleton
    @Provides
    fun settingsRepository(
        @ApplicationContext context: Context, @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SettingsRepository = SettingsRepositoryImpl(
        ioDispatcher = ioDispatcher, contentResolver = context.contentResolver
    )
}