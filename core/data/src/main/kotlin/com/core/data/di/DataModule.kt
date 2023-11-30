package com.core.data.di

import com.core.data.repository.SettingsRepositoryImpl
import com.core.data.repository.UserAppSettingsRepositoryImpl
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.UserAppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun userAppSettingsRepository(impl: UserAppSettingsRepositoryImpl): UserAppSettingsRepository

    @Binds
    @Singleton
    fun settingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}