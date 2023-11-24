package com.core.domain.usecase.userappsettings

import com.core.domain.usecase.ValidatePackageName

data class UserAppSettingsUseCases(
    val validateAppName: ValidateAppName,
    val validatePackageName: ValidatePackageName,
    val validateUserAppSettingsList: ValidateUserAppSettingsList
)
