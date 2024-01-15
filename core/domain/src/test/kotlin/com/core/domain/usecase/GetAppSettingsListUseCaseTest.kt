package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetAppSettingsListUseCaseTest {
    private lateinit var getAppSettingsListUseCase: GetAppSettingsListUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    @Before
    fun setUp() {
        settingsRepository = TestSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        getAppSettingsListUseCase = GetAppSettingsListUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )
    }

    @Test
    fun `If settings key existed in secure settings with SettingsType as System then update the each AppSettingsItem safeToWrite to true`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = settingsKeyToTest, value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertTrue { appSettingsList.any { it.safeToWrite } }
        }

    @Test
    fun `If settings key does not exist in secure settings with SettingsType as System then update the each AppSettingsItem safeToWrite to false`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = "Key", value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertFalse { appSettingsList.any { it.safeToWrite } }
        }

    @Test
    fun `If settings key existed in secure settings with SettingsType as Secure then update the each AppSettingsItem safeToWrite to true`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SECURE,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = settingsKeyToTest, value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertTrue { appSettingsList.any { it.safeToWrite } }
        }

    @Test
    fun `If settings key does not exist in secure settings with SettingsType as Secure then update the each AppSettingsItem safeToWrite to false`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SECURE,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = "Key", value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertFalse { appSettingsList.any { it.safeToWrite } }
        }

    @Test
    fun `If settings key existed in secure settings with SettingsType as Global then update the each AppSettingsItem safeToWrite to true`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.GLOBAL,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = settingsKeyToTest, value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertTrue { appSettingsList.any { it.safeToWrite } }
        }

    @Test
    fun `If settings key does not exist in secure settings with SettingsType as Global then update the each AppSettingsItem safeToWrite to false`() =
        runTest {
            val settingsKeyToTest = "settingsKeyTest"

            val packageNameTest = "packageNameTest"

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.GLOBAL,
                        packageName = packageNameTest,
                        label = "system",
                        key = settingsKeyToTest,
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            settingsRepository.sendSecureSettings(
                listOf(
                    SecureSettings(id = 0, name = "Key", value = "0")
                )
            )

            val appSettingsList = getAppSettingsListUseCase(packageNameTest).first()

            assertFalse { appSettingsList.any { it.safeToWrite } }
        }
}