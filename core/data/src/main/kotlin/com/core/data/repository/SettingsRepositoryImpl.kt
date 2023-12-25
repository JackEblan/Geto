package com.core.data.repository

import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.IO
import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.UserAppSettingsItem
import com.core.sharedpreferences.SystemSettingsDataSourceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val systemSettingsDataSourceImpl: SystemSettingsDataSourceImpl
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            systemSettingsDataSourceImpl.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnLaunch },
                successMessage = "Settings has been applied"
            )
        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            systemSettingsDataSourceImpl.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnRevert },
                successMessage = "Settings has been reverted"
            )
        }
    }
}