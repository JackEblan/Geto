package com.core.database.di

import android.content.Context
import androidx.room.Room
import com.core.database.AppDatabase
import com.core.database.migration.Migration1To3
import com.core.database.migration.Migration2To3
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
    ).addMigrations(Migration1To3(), Migration2To3()).build()
}