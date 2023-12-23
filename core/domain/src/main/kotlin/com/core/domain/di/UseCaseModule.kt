package com.core.domain.di

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
}