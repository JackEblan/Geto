package com.core.data.repository

import com.core.domain.util.SettingsWriteable
import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.UserAppSettingsItem
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsWriteable: SettingsWriteable
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = settingsWriteable.canWriteSettings(
                    userAppSettingsItem = userAppSettingsItem,
                    valueSelector = { userAppSettingsItem.valueOnLaunch })
                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            "Settings applied"

        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = settingsWriteable.canWriteSettings(
                    userAppSettingsItem = userAppSettingsItem,
                    valueSelector = { userAppSettingsItem.valueOnRevert })
                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            "Settings reverted"

        }
    }
}