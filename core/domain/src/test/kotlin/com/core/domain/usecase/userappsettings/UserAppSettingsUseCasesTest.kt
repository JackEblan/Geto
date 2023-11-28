package com.core.domain.usecase.userappsettings

import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserAppSettingsUseCasesTest {

    private lateinit var validateUserAppSettingsList: ValidateUserAppSettingsList

    @Before
    fun setup() {
        validateUserAppSettingsList = ValidateUserAppSettingsList()
    }

    @Test
    fun `When user app settings list is empty or no enabled settings, return error`() {
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

        assertEquals(result.successful, false)
    }
}