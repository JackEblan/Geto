package com.core.data.di

import com.core.data.repository.DefaultAppSettingsRepository
import com.core.data.repository.DefaultClipboardRepository
import com.core.data.repository.DefaultPackageRepository
import com.core.data.repository.DefaultSecureSettingsRepository
import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.PackageRepository
import com.core.domain.repository.SecureSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun appSettingsRepository(impl: DefaultAppSettingsRepository): AppSettingsRepository

    @Binds
    @Singleton
    fun secureSettingsRepository(impl: DefaultSecureSettingsRepository): SecureSettingsRepository

    @Binds
    @Singleton
    fun packageRepository(impl: DefaultPackageRepository): PackageRepository

    @Binds
    @Singleton
    fun clipboardRepository(impl: DefaultClipboardRepository): ClipboardRepository
}