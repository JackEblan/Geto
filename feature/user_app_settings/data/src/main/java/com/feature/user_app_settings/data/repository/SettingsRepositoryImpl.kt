package com.feature.user_app_settings.data.repository

import android.content.ContentResolver
import android.provider.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val contentResolver: ContentResolver
) : com.feature.user_app_settings.domain.repository.SettingsRepository {
    override suspend fun applySettings(userAppSettingsItemList: List<com.feature.user_app_settings.domain.model.UserAppSettingsItem>): Result<com.feature.user_app_settings.domain.repository.ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            onResult(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnLaunch },
                successMessage = "Settings has been applied"
            )
        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<com.feature.user_app_settings.domain.model.UserAppSettingsItem>): Result<com.feature.user_app_settings.domain.repository.ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            onResult(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnRevert },
                successMessage = "Settings has been reverted"
            )
        }
    }

    private fun onResult(
        userAppSettingsItemList: List<com.feature.user_app_settings.domain.model.UserAppSettingsItem>,
        valueSelector: (com.feature.user_app_settings.domain.model.UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<com.feature.user_app_settings.domain.repository.ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = when (userAppSettingsItem.settingsType) {
                    com.feature.user_app_settings.domain.model.SettingsType.SYSTEM -> Settings.System.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    com.feature.user_app_settings.domain.model.SettingsType.SECURE -> Settings.Secure.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    com.feature.user_app_settings.domain.model.SettingsType.GLOBAL -> Settings.Global.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )
                }
                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            successMessage

        }
    }
}