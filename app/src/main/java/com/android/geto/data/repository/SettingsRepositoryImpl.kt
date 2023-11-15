package com.android.geto.data.repository

import android.content.ContentResolver
import android.provider.Settings
import com.android.geto.domain.model.UserAppSettingsItem
import com.android.geto.domain.repository.SetSettingsResultMessage
import com.android.geto.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class SettingsRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, private val contentResolver: ContentResolver
) : SettingsRepository {
    override suspend fun setSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Flow<Result<SetSettingsResultMessage>> =
        flow {
            val result = withContext(ioDispatcher) {
                applySettings(
                    userAppSettingsItemList = userAppSettingsItemList,
                    valueSelector = { it.valueOnLaunch },
                    successMessage = "Settings has been applied"
                )
            }

            emit(result)
        }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Flow<Result<SetSettingsResultMessage>> =
        flow {
            val result = withContext(ioDispatcher) {
                applySettings(
                    userAppSettingsItemList = userAppSettingsItemList,
                    valueSelector = { it.valueOnRevert },
                    successMessage = "Settings has been reverted"
                )
            }

            emit(result)
        }

    private suspend fun applySettings(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<SetSettingsResultMessage> {
        return suspendCancellableCoroutine { continuation ->
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                    try {
                        val successful = when (userAppSettingsItem.settingsType) {
                            0 -> Settings.System.putString(
                                contentResolver,
                                userAppSettingsItem.key,
                                valueSelector(userAppSettingsItem)
                            )

                            1 -> Settings.Secure.putString(
                                contentResolver,
                                userAppSettingsItem.key,
                                valueSelector(userAppSettingsItem)
                            )

                            2 -> Settings.Global.putString(
                                contentResolver,
                                userAppSettingsItem.key,
                                valueSelector(userAppSettingsItem)
                            )

                            else -> false
                        }

                        if (successful) {
                            continuation.resume(Result.success(successMessage))
                        } else {
                            continuation.resume(
                                Result.failure(
                                    IllegalArgumentException("${userAppSettingsItem.key} cannot be found")
                                )
                            )
                        }

                    } catch (e: SecurityException) {
                        continuation.resume(Result.failure(e))
                    }
                }
        }
    }
}