package com.feature.userappsettings

import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
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
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
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
    fun `Launch app returns Result success`() = runTest {
        settingsRepository.setWriteableSettings(true)

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
    fun `Revert settings returns Result success`() = runTest {
        settingsRepository.setWriteableSettings(true)

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
    fun `Check enabled UserAppSettingsItem returns success with enabled to true`() =
        runTest {
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
    fun `Delete UserAppSettingsItem returns success`() = runTest {
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

    @Test
    fun `Launch app returns Result failure`() = runTest {
        settingsRepository.setWriteableSettings(false)

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

        assertFails { settingsRepository.applySettings(settings).getOrThrow() }
    }

    @Test
    fun `Revert settings returns Result failure`() = runTest {
        settingsRepository.setWriteableSettings(false)

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

        assertFails { settingsRepository.revertSettings(settings).getOrThrow() }
    }
}