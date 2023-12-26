package com.feature.userappsettings.components.dialog

import com.core.model.SettingsType
import com.core.model.UserAppSettings
import com.core.testing.repository.TestUserAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddSettingsDialogViewModelTest {
    private lateinit var userAppSettingsRepository: TestUserAppSettingsRepository

    @Before
    fun setUp() {
        userAppSettingsRepository = TestUserAppSettingsRepository()
    }

    @Test
    fun `Add UserAppSettingsItem then item existed`() = runTest {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key",
            valueOnLaunch = "",
            valueOnRevert = ""
        )

        userAppSettingsRepository.upsertUserAppSettings(userAppSettings)

        assertTrue {
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()
                .contains(userAppSettings)
        }
    }
}