package com.core.data.di

import com.core.data.wrapper.DefaultBuildVersionWrapper
import com.core.data.wrapper.DefaultClipboardManagerWrapper
import com.core.data.wrapper.DefaultPackageManagerWrapper
import com.core.data.wrapper.DefaultSecureSettingsPermissionWrapper
import com.core.domain.wrapper.BuildVersionWrapper
import com.core.domain.wrapper.ClipboardManagerWrapper
import com.core.domain.wrapper.PackageManagerWrapper
import com.core.domain.wrapper.SecureSettingsPermissionWrapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WrapperModule {

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

}