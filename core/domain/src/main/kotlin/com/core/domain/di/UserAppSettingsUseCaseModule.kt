package com.core.domain.di

import com.core.domain.usecase.addsettings.AddSettingsUseCases
import com.core.domain.usecase.addsettings.ValidateKey
import com.core.domain.usecase.addsettings.ValidateLabel
import com.core.domain.usecase.addsettings.ValidateValueOnLaunch
import com.core.domain.usecase.addsettings.ValidateValueOnRevert
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserAppSettingsUseCaseModule {

    @Singleton
    @Provides
    fun addSettingsUseCases(): AddSettingsUseCases = AddSettingsUseCases(
        validateLabel = ValidateLabel(),
        validateKey = ValidateKey(),
        validateValueOnLaunch = ValidateValueOnLaunch(),
        validateValueOnRevert = ValidateValueOnRevert()
    )

    @Singleton
    @Provides
    fun validateUserAppSettingsList(): ValidateUserAppSettingsList = ValidateUserAppSettingsList()
}