package com.feature.user_app_list.data.repository

import android.content.Context
import com.feature.user_app_list.domain.repository.UserAppListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun userAppListRepository(
        @ApplicationContext context: Context,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): UserAppListRepository = UserAppListRepositoryImpl(
        defaultDispatcher = defaultDispatcher, packageManager = context.packageManager
    )
}