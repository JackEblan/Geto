package com.android.geto.core.database.di

import android.content.Context
import androidx.room.Room
import com.android.geto.core.database.AppDatabase
import com.android.geto.core.database.migration.Migration1To3
import com.android.geto.core.database.migration.Migration1To4
import com.android.geto.core.database.migration.Migration1To5
import com.android.geto.core.database.migration.Migration2To3
import com.android.geto.core.database.migration.Migration3To4
import com.android.geto.core.database.migration.Migration4To5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun appDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, AppDatabase.DATABASE_NAME
    ).addMigrations(
        Migration1To3(),
        Migration1To4(),
        Migration1To5(),
        Migration2To3(),
        Migration3To4(),
        Migration4To5()
    ).build()
}