package com.feature.userappsettings

import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.repository.TestUserAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UserAppSettingsViewModelTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var userAppSettingsRepository: TestUserAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: UserAppSettingsViewModel


    @Before
    fun setup() {
        userAppSettingsRepository = TestUserAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        mockPackageManager = mock()

        viewModel = UserAppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            userAppSettingsRepository = userAppSettingsRepository,
            settingsRepository = settingsRepository,
            validateUserAppSettingsList = ValidateUserAppSettingsList(),
            packageManager = mockPackageManager
        )
    }

    @Test
    fun `Apply settings returns Result success`() = runTest {
        settingsRepository.setWriteSecureSettings(true)

        val settings = listOf(
            UserAppSettings(
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

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert settings returns Result success`() = runTest {
        settingsRepository.setWriteSecureSettings(true)

        val settings = listOf(
            UserAppSettings(
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

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Check UserAppSettingsItem enabled to true then item is updated`() = runTest {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key",
            valueOnLaunch = "",
            valueOnRevert = ""
        )

        userAppSettingsRepository.upsertUserAppSettingsEnabled(userAppSettings)

        val updateduserAppSettingsItem =
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()

        assertTrue { updateduserAppSettingsItem.first { it.key == userAppSettings.key }.enabled }
    }

    @Test
    fun `Delete UserAppSettingsItem then item not existed`() = runTest {
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

        userAppSettingsRepository.deleteUserAppSettings(userAppSettings)

        assertFalse {
            userAppSettingsRepository.getUserAppSettingsList("com.android.geto").first()
                .contains(userAppSettings)
        }
    }

    @Test
    fun `Apply settings returns Result failure`() = runTest {
        settingsRepository.setWriteSecureSettings(false)

        val settings = listOf(
            UserAppSettings(
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

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert settings returns Result failure`() = runTest {
        settingsRepository.setWriteSecureSettings(false)

        val settings = listOf(
            UserAppSettings(
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

        assertTrue { result.isFailure }
    }
}