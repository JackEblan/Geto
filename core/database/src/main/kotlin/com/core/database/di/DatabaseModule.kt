package com.core.database.di

import android.content.Context
import androidx.room.Room
import com.core.database.AppDatabase
import com.core.database.MIGRATION_2_3
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
    ).addMigrations(MIGRATION_2_3).build()


}