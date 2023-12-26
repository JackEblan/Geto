package com.core.testing.repository

import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.UserAppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestUserAppSettingsRepository : UserAppSettingsRepository {

    private val _userAppSettings = MutableStateFlow<List<UserAppSettings>>(emptyList())

    override suspend fun upsertUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { currentList ->

            val updatedList = currentList.toMutableList()

            updatedList.find { it.key == userAppSettings.key }?.let {
                updatedList.remove(it)
            }

            updatedList.add(userAppSettings)

            updatedList
        }

        return if (_userAppSettings.first().contains(userAppSettings)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { currentList ->

            val updatedList = currentList.toMutableList()

            updatedList.find { it.key == userAppSettings.key }?.let {
                updatedList.remove(it)
            }

            updatedList.add(userAppSettings)

            updatedList
        }

        return if (_userAppSettings.first().contains(userAppSettings)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun deleteUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { entities ->
            entities.filterNot { it.key == userAppSettings.key }
        }

        return if (!_userAppSettings.first().contains(userAppSettings)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettings>> {
        return _userAppSettings.asStateFlow().map { userAppSettingsItemEntities ->
            userAppSettingsItemEntities.filter { it.packageName == packageName }
        }
    }
}