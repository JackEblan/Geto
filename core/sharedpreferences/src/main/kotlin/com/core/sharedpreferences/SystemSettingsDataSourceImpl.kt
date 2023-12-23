package com.core.sharedpreferences

import android.content.Context
import android.provider.Settings
import com.core.common.di.IoDispatcher
import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SystemSettingsDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext context: Context
) : SystemSettingsDataSource {

    private val contentResolver = context.contentResolver

    override suspend fun putSystemPreferences(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            runCatching {
                userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                    val successful = when (userAppSettingsItem.settingsType) {
                        SettingsType.SYSTEM -> Settings.System.putString(
                            contentResolver,
                            userAppSettingsItem.key,
                            valueSelector(userAppSettingsItem)
                        )

                        SettingsType.SECURE -> Settings.Secure.putString(
                            contentResolver,
                            userAppSettingsItem.key,
                            valueSelector(userAppSettingsItem)
                        )

                        SettingsType.GLOBAL -> Settings.Global.putString(
                            contentResolver,
                            userAppSettingsItem.key,
                            valueSelector(userAppSettingsItem)
                        )
                    }
                    check(successful) { "${userAppSettingsItem.key} failed to apply" }
                }

                successMessage

            }
        }
    }
}