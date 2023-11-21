package com.android.geto.di

import android.content.Context
import android.content.pm.PackageManager
import androidx.room.Room
import com.android.geto.data.local.AppDatabase
import com.android.geto.data.repository.SettingsRepositoryImpl
import com.android.geto.data.repository.UserAppSettingsRepositoryImpl
import com.android.geto.domain.repository.SettingsRepository
import com.android.geto.domain.repository.UserAppSettingsRepository
import com.android.geto.domain.use_case.ValidatePackageName
import com.android.geto.domain.use_case.add_settings.AddSettingsUseCases
import com.android.geto.domain.use_case.add_settings.ValidateKey
import com.android.geto.domain.use_case.add_settings.ValidateLabel
import com.android.geto.domain.use_case.add_settings.ValidateValueOnLaunch
import com.android.geto.domain.use_case.add_settings.ValidateValueOnRevert
import com.android.geto.domain.use_case.user_app_settings.UserAppSettingsUseCases
import com.android.geto.domain.use_case.user_app_settings.ValidateAppName
import com.android.geto.domain.use_case.user_app_settings.ValidateUserAppSettingsList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun packageManager(@ApplicationContext context: Context): PackageManager =
        context.packageManager

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

    @Singleton
    @Provides
    fun addSettingsUseCases(): AddSettingsUseCases = AddSettingsUseCases(
        validateLabel = ValidateLabel(),
        validateKey = ValidateKey(),
        validateValueOnLaunch = ValidateValueOnLaunch(),
        validateValueOnRevert = ValidateValueOnRevert(),
        validatePackageName = ValidatePackageName()
    )

    @Singleton
    @Provides
    fun userAppSettingsUseCases(): UserAppSettingsUseCases = UserAppSettingsUseCases(
        validateAppName = ValidateAppName(),
        validatePackageName = ValidatePackageName(),
        validateUserAppSettingsList = ValidateUserAppSettingsList()
    )
}