package com.core.sharedpreferences.di

import android.content.Context
import com.core.sharedpreferences.system.SystemSettingsSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun systemSettingsSharedPreferences(@ApplicationContext context: Context) =
        SystemSettingsSharedPreferences(context.contentResolver)
}