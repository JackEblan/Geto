package com.core.domain.di

import android.content.pm.PackageManager
import com.core.domain.usecase.userapplist.GetNonSystemApps
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun validateUserAppSettingsList(): ValidateUserAppSettingsList = ValidateUserAppSettingsList()

    @Singleton
    @Provides
    fun getNonSystemApps(packageManager: PackageManager): GetNonSystemApps = GetNonSystemApps(packageManager)
}