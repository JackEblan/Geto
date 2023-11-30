package com.core.systemmanagers.di

import com.core.systemmanagers.PackageManagerHelper
import com.core.systemmanagers.packagemanager.PackageManagerHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SystemManagersModule {
    @Binds
    @Singleton
    fun packageManagerHelper(impl: PackageManagerHelperImpl): PackageManagerHelper
}