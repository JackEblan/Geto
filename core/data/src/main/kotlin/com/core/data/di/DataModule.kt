package com.core.data.di

import com.core.data.repository.DefaultAppSettingsRepository
import com.core.data.repository.DefaultClipboardRepository
import com.core.data.repository.DefaultPackageRepository
import com.core.data.repository.DefaultSettingsRepository
import com.core.data.util.DefaultBuildVersionWrapper
import com.core.data.util.DefaultClipboardManagerWrapper
import com.core.data.util.DefaultPackageManagerWrapper
import com.core.data.util.DefaultSecureSettingsPermissionWrapper
import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.PackageRepository
import com.core.domain.repository.SettingsRepository
import com.core.domain.util.BuildVersionWrapper
import com.core.domain.util.ClipboardManagerWrapper
import com.core.domain.util.PackageManagerWrapper
import com.core.domain.util.SecureSettingsPermissionWrapper
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
    fun userAppSettingsRepository(impl: DefaultAppSettingsRepository): AppSettingsRepository

    @Binds
    @Singleton
    fun settingsRepository(impl: DefaultSettingsRepository): SettingsRepository

    @Binds
    @Singleton
    fun packageRepository(impl: DefaultPackageRepository): PackageRepository

    @Binds
    @Singleton
    fun secureSettingsPermissionWrapper(impl: DefaultSecureSettingsPermissionWrapper): SecureSettingsPermissionWrapper

    @Binds
    @Singleton
    fun buildVersionWrapper(impl: DefaultBuildVersionWrapper): BuildVersionWrapper

    @Binds
    @Singleton
    fun packageManagerWrapper(impl: DefaultPackageManagerWrapper): PackageManagerWrapper

    @Binds
    @Singleton
    fun clipboardManagerWrapper(impl: DefaultClipboardManagerWrapper): ClipboardManagerWrapper

    @Binds
    @Singleton
    fun clipboardRepository(impl: DefaultClipboardRepository): ClipboardRepository
}