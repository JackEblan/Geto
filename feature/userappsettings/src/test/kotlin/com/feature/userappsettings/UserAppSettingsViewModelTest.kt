package com.feature.userappsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.core.testing.repository.TestPackageManagerHelper
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.repository.TestUserAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserAppSettingsViewModelTest {

    private lateinit var userAppSettingsRepository: TestUserAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private lateinit var packageManagerHelper: TestPackageManagerHelper

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: UserAppSettingsViewModel


    @Before
    fun setup() {
        userAppSettingsRepository = TestUserAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        packageManagerHelper = TestPackageManagerHelper()

        viewModel = UserAppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            userAppSettingsRepository = userAppSettingsRepository,
            settingsRepository = settingsRepository,
            validateUserAppSettingsList = ValidateUserAppSettingsList(),
            packageManagerHelper = packageManagerHelper
        )
    }

    @Test
    fun `Launch app returns result`() = runTest {
        val settings = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )
        val result = settingsRepository.applySettings(settings)

        assertNotNull(result)
    }

    @Test
    fun `Revert settings returns result`() = runTest {
        val settings = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )
        val result = settingsRepository.revertSettings(settings)

        assertNotNull(result)
    }

    @Test
    fun `Check enabled UserAppSettingsItem returns true`() = runTest {
        val userAppSettingsItem = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key",
            valueOnLaunch = "",
            valueOnRevert = ""
        )

        userAppSettingsRepository.upsertUserAppSettingsEnabled(userAppSettingsItem)

        val updateduserAppSettingsItem =
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()

        assertTrue { updateduserAppSettingsItem.first { it.key == userAppSettingsItem.key }.enabled }
    }

    @Test
    fun `Delete settings returns result`() = runTest {
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

        userAppSettingsRepository.deleteUserAppSettings(userAppSettingsItem)

        assertFalse {
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()
                .contains(userAppSettingsItem)
        }
    }
}