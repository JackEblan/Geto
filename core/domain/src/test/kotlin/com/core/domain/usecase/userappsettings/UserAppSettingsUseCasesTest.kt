package com.core.domain.usecase.userappsettings

import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class UserAppSettingsUseCasesTest {

    private lateinit var validateUserAppSettingsList: ValidateUserAppSettingsList

    @Before
    fun setup() {
        validateUserAppSettingsList = ValidateUserAppSettingsList()
    }

    @Test
    fun `When user app settings list has no enabled settings, return false`() {
        val userAppSettingsList = listOf(
            UserAppSettingsItem(
                enabled = false,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.settings",
                label = "Settings",
                key = "com.android.settings",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = validateUserAppSettingsList(userAppSettingsList)

        assertThat(result.successful).isFalse()
    }

    @Test
    fun `When user app settings list is empty, return false`() {
        val result = validateUserAppSettingsList(emptyList())

        assertThat(result.successful).isFalse()
    }
}