package com.core.sharedpreferences.di

import com.core.sharedpreferences.SystemSettingsDataSource
import com.core.sharedpreferences.system.SystemSettingsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferencesModule {

    @Binds
    @Singleton
    fun systemSettingsSharedPreferences(impl: SystemSettingsDataSourceImpl): SystemSettingsDataSource
}