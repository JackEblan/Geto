/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.model.TargetShortcutInfoCompat
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestClipboardRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.repository.TestShortcutRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import com.android.geto.feature.appsettings.navigation.APP_NAME_ARG
import com.android.geto.feature.appsettings.navigation.PACKAGE_NAME_ARG
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageRepository: TestPackageRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var shortcutRepository: TestShortcutRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel

    private val packageNameTest = "packageNameTest"

    private val appNameTest = "appNameTest"

    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        packageRepository = TestPackageRepository()

        shortcutRepository = TestShortcutRepository()

        savedStateHandle[PACKAGE_NAME_ARG] = packageNameTest

        savedStateHandle[APP_NAME_ARG] = appNameTest

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            clipboardRepository = clipboardRepository,
            secureSettingsRepository = secureSettingsRepository,
            shortcutRepository = shortcutRepository,
            packageRepository = packageRepository,
            applyAppSettingsUseCase = ApplyAppSettingsUseCase(
                appSettingsRepository = appSettingsRepository,
                secureSettingsRepository = secureSettingsRepository
            ),
            revertAppSettingsUseCase = RevertAppSettingsUseCase(
                appSettingsRepository = appSettingsRepository,
                secureSettingsRepository = secureSettingsRepository
            )
        )
    }

    @Test
    fun appSettingsUiStateIsLoading_whenStarted() = runTest {
        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Loading>(item)
    }

    @Test
    fun appSettingsUiStateIsSuccess_whenDataIsNotEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingsUiState.collect() }

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun appSettingsUiStateIsEmpty_whenDataIsEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingsUiState.collect() }

        appSettingsRepository.setAppSettings(emptyList())

        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Empty>(item)

        collectJob.cancel()
    }


    @Test
    fun snackBarIsNotNull_whenLaunchApp_emptyAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.launchApp()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun snackBarIsNotNull_whenLaunchApp_itemEnabledIsFalse() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.launchApp()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun launchIntentIsNotNull_whenLaunchApp() = runTest {
        packageRepository.setNonSystemApps(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = packageNameTest, label = "label"
                )
            )
        )

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.launchApp()

        assertNotNull(viewModel.launchAppIntent.value)

    }

    @Test
    fun applicationIconIsNotNull_whenGetApplicationIcon() = runTest {
        packageRepository.setNonSystemApps(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = packageNameTest, label = "label"
                )
            )
        )

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.getApplicationIcon()

        assertNotNull(viewModel.icon.value)
    }

    @Test
    fun copyPermissionDialogIsTrue_whenLaunchApp_writeSecureSettingsIsFalse() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(false)

        viewModel.launchApp()

        val item = viewModel.showCopyPermissionCommandDialog.value

        assertTrue(item)
    }

    @Test
    fun snackBarIsNotNull_whenRevertSettings_emptyAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun snackBarIsNotNull_whenRevertSettings_itemEnabledIsFalse() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun snackBarIsNotNull_whenRevertSettings() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun copyPermissionDialogIsTrue_whenRevertSettings_writeSecureSettingsIsFalse() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(false)

        viewModel.revertSettings()

        val item = viewModel.showCopyPermissionCommandDialog.value

        assertTrue(item)
    }

    @Test
    fun copyPermissionDialogIsFalse_whenCopyPermissionCommand() = runTest {

        viewModel.copyPermissionCommand()

        val item = viewModel.showCopyPermissionCommandDialog.value

        assertFalse(item)
    }

    @Test
    fun secureSettingsIsNotEmpty_whenGetSecureSettings() = runTest {
        secureSettingsRepository.setSecureSettings(testSecureSettingsList)

        viewModel.getSecureSettings(text = "name0", settingsType = SettingsType.GLOBAL)

        val item = viewModel.secureSettings.value

        assertTrue(item.isNotEmpty())
    }

    @Test
    fun secureSettingsIsEmpty_whenGetSecureSettings() = runTest {
        secureSettingsRepository.setSecureSettings(testSecureSettingsList)

        viewModel.getSecureSettings(text = "nameNotFound", settingsType = SettingsType.GLOBAL)

        val item = viewModel.secureSettings.value

        assertTrue(item.isEmpty())
    }

    @Test
    fun shortcutIsNull_whenGetShortcut() = runTest {
        shortcutRepository.setShortcuts(testShortcutsLists)

        viewModel.getShortcut("idNotFound")

        val item = viewModel.shortcut.value

        assertNull(item)
    }

    @Test
    fun shortcutIsNotNull_whenGetShortcut() = runTest {
        shortcutRepository.setShortcuts(testShortcutsLists)

        viewModel.getShortcut("id0")

        val item = viewModel.shortcut.value

        assertNotNull(item)
    }

    @Test
    fun snackBarIsNotNull_whenRequestPinShortcut() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(true)

        viewModel.requestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(
            expected = "Your current launcher supports shortcuts", actual = viewModel.snackBar.value
        )
    }

    @Test
    fun snackBarIsNotNull_whenRequestPinShortcut_requestPinShortcutSupportedIsFalse() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(false)

        viewModel.requestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(
            expected = "Your current launcher does not support shortcuts",
            actual = viewModel.snackBar.value
        )
    }

    @Test
    fun snackBarIsNotNull_whenUpdateRequestPinShortcut() = runTest {
        shortcutRepository.setUpdateImmutableShortcuts(true)

        viewModel.updateRequestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(
            expected = "Trying to update immutable shortcuts", actual = viewModel.snackBar.value
        )
    }

    @Test
    fun snackBarIsNotNull_whenUpdateRequestPinShortcut_updateImmutableShortcutsIsFalse() = runTest {
        shortcutRepository.setUpdateImmutableShortcuts(false)

        viewModel.updateRequestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(
            expected = "Shortcut updated successfully", actual = viewModel.snackBar.value
        )
    }
}

private val testSecureSettingsList = listOf(
    SecureSettings(id = 0L, name = "name0", value = "value0"),
    SecureSettings(id = 1L, name = "name1", value = "value1")
)

private val testShortcutsLists = listOf(
    TargetShortcutInfoCompat(id = "id0"),
    TargetShortcutInfoCompat(id = "id1"),
)