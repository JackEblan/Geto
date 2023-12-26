package com.feature.userappsettings.components.dialog

import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
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
    fun `Add UserAppSettingsItem returns success`() = runTest {
        val userAppSettingsItem = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key",
            valueOnLaunch = "",
            valueOnRevert = ""
        )

        userAppSettingsRepository.upsertUserAppSettings(userAppSettingsItem)

        assertTrue {
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()
                .contains(userAppSettingsItem)
        }
    }
}