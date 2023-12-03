package com.core.data.repository

import com.core.common.di.IoDispatcher
import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.UserAppSettingsItem
import com.core.sharedpreferences.system.SystemSettingsSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val systemSettingsSharedPreferences: SystemSettingsSharedPreferences
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            systemSettingsSharedPreferences.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnLaunch },
                successMessage = "Settings has been applied"
            )
        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            systemSettingsSharedPreferences.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnRevert },
                successMessage = "Settings has been reverted"
            )
        }
    }
}