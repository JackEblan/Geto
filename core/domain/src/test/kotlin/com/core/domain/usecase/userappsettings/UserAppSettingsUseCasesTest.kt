package com.core.domain.usecase.userappsettings

import com.core.model.SettingsType
import com.core.model.UserAppSettings
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
    fun `When user app settings list has no enabled settings, return false`() {
        val userAppSettingsList = listOf(
            UserAppSettings(
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

        assertEquals(expected = result.successful, actual = false)
    }

    @Test
    fun `When user app settings list is empty, return false`() {
        val result = validateUserAppSettingsList(emptyList())

        assertEquals(expected = false, actual = result.successful)
    }

    @Test
    fun `When user app settings list is not empty and all items are enabled, return true`() {
        val userAppSettingsList = listOf(
            UserAppSettings(
                enabled = true,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.settings",
                label = "Settings",
                key = "com.android.settings",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = validateUserAppSettingsList(userAppSettingsList)

        assertEquals(expected = true, actual = result.successful)
    }
}