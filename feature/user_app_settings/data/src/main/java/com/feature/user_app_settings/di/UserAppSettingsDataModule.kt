package com.feature.user_app_settings.di

import android.content.Context
import androidx.room.Room
import com.core.common.di.IoDispatcher
import com.core.local.AppDatabase
import com.feature.user_app_settings.data.repository.SettingsRepositoryImpl
import com.feature.user_app_settings.data.repository.UserAppSettingsRepositoryImpl
import com.feature.user_app_settings.domain.repository.SettingsRepository
import com.feature.user_app_settings.domain.repository.UserAppSettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserAppSettingsDataModule {

    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).build()

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