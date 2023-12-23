package com.core.packagemanager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PackageManagerModule {
    @Binds
    @Singleton
    fun packageManagerDataSource(impl: PackageManagerDataSourceImpl): PackageManagerDataSource
}