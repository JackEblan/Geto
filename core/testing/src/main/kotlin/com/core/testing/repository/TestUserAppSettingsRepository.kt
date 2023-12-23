package com.core.testing.repository

import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestUserAppSettingsRepository : UserAppSettingsRepository {

    private val _userAppSettings = MutableStateFlow<List<UserAppSettingsItem>>(emptyList())

    override suspend fun upsertUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { currentList ->

            val updatedList = currentList.toMutableList()

            updatedList.find { it.key == userAppSettingsItem.key }?.let {
                updatedList.remove(it)
            }

            updatedList.add(userAppSettingsItem)

            updatedList
        }

        return if (_userAppSettings.first().contains(userAppSettingsItem)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { currentList ->

            val updatedList = currentList.toMutableList()

            updatedList.find { it.key == userAppSettingsItem.key }?.let {
                updatedList.remove(it)
            }

            updatedList.add(userAppSettingsItem)

            updatedList
        }

        return if (_userAppSettings.first().contains(userAppSettingsItem)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun deleteUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        _userAppSettings.update { entities ->
            entities.filterNot { it.key == userAppSettingsItem.key }
        }

        return if (!_userAppSettings.first().contains(userAppSettingsItem)) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItem>> {
        return _userAppSettings.asStateFlow().map { userAppSettingsItemEntities ->
            userAppSettingsItemEntities.filter { it.packageName == packageName }
        }
    }
}