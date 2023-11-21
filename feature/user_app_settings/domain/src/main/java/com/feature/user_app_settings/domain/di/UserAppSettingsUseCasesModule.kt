package com.feature.user_app_settings.domain.di

import com.feature.user_app_settings.domain.use_case.ValidatePackageName
import com.feature.user_app_settings.domain.use_case.add_settings.AddSettingsUseCases
import com.feature.user_app_settings.domain.use_case.add_settings.ValidateKey
import com.feature.user_app_settings.domain.use_case.add_settings.ValidateLabel
import com.feature.user_app_settings.domain.use_case.add_settings.ValidateValueOnLaunch
import com.feature.user_app_settings.domain.use_case.add_settings.ValidateValueOnRevert
import com.feature.user_app_settings.domain.use_case.user_app_settings.UserAppSettingsUseCases
import com.feature.user_app_settings.domain.use_case.user_app_settings.ValidateAppName
import com.feature.user_app_settings.domain.use_case.user_app_settings.ValidateUserAppSettingsList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserAppSettingsUseCasesModule {

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