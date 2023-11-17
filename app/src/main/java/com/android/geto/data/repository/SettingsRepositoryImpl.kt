package com.android.geto.data.repository

import android.content.ContentResolver
import android.provider.Settings
import com.android.geto.domain.model.SettingsType
import com.android.geto.domain.model.UserAppSettingsItem
import com.android.geto.domain.repository.ApplySettingsResultMessage
import com.android.geto.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SettingsRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val contentResolver: ContentResolver
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            onResult(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnLaunch },
                successMessage = "Settings has been applied"
            )
        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return withContext(ioDispatcher) {
            onResult(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { it.valueOnRevert },
                successMessage = "Settings has been reverted"
            )
        }
    }

    private suspend fun onResult(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage> {
        return suspendCancellableCoroutine { continuation ->
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                try {
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

                    if (successful) {
                        continuation.resume(Result.success(successMessage))
                    } else {
                        continuation.resumeWithException(IllegalArgumentException("${userAppSettingsItem.key} failed to apply"))
                    }

                } catch (e: SecurityException) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }
}