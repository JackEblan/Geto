package com.core.sharedpreferences

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.UserAppSettingsItem

interface SystemSettingsDataSource {
    suspend fun putSystemPreferences(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage>

}