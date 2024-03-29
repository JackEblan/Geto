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
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.domain.AppSettingsResult
import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.domain.AutoLaunchUseCase
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.model.TargetShortcutInfoCompat
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestClipboardRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.repository.TestShortcutRepository
import com.android.geto.core.testing.repository.TestUserDataRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import com.android.geto.feature.appsettings.navigation.APP_NAME_ARG
import com.android.geto.feature.appsettings.navigation.PACKAGE_NAME_ARG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val packageRepository = TestPackageRepository()

    private val appSettingsRepository = TestAppSettingsRepository()

    private val secureSettingsRepository = TestSecureSettingsRepository()

    private val clipboardRepository = TestClipboardRepository()

    private val shortcutRepository = TestShortcutRepository()

    private val userDataRepository = TestUserDataRepository()

    private val savedStateHandle = SavedStateHandle()

    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var autoLaunchUseCase: AutoLaunchUseCase

    private lateinit var viewModel: AppSettingsViewModel

    private val packageName = "com.android.geto"

    private val appName = "Geto"

    @Before
    fun setup() {
        savedStateHandle[PACKAGE_NAME_ARG] = packageName

        savedStateHandle[APP_NAME_ARG] = appName

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            packageRepository = packageRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )

        autoLaunchUseCase = AutoLaunchUseCase(
            packageRepository = packageRepository,
            userDataRepository = userDataRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository

        )

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            clipboardRepository = clipboardRepository,
            packageRepository = packageRepository,
            secureSettingsRepository = secureSettingsRepository,
            shortcutRepository = shortcutRepository,
            applyAppSettingsUseCase = applyAppSettingsUseCase,
            revertAppSettingsUseCase = revertAppSettingsUseCase,
            autoLaunchUseCase = autoLaunchUseCase
        )
    }

    @Test
    fun appSettingsUiState_isLoading_whenStarted() = runTest {
        assertIs<AppSettingsUiState.Loading>(viewModel.appSettingUiState.value)
    }

    @Test
    fun appSettingsUiState_isSuccess_whenAppSettings_isNotEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingUiState.collect() }

        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        val item = viewModel.appSettingUiState.value

        assertIs<AppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun applyAppSettingsResult_isSuccess_whenApplySettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applySettings()

        assertIs<AppSettingsResult.Success>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun applyAppSettingsResult_isSecurityException_whenApplySettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(false)

        viewModel.applySettings()

        assertIs<AppSettingsResult.SecurityException>(viewModel.applyAppSettingsResult.value)

    }

    @Test
    fun applyAppSettingsResult_isIllegalArgumentException_whenApplySettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        viewModel.applySettings()

        assertIs<AppSettingsResult.IllegalArgumentException>(viewModel.applyAppSettingsResult.value)

    }

    @Test
    fun applyAppSettingsResult_isEmptyAppSettings_whenApplySettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applySettings()

        assertIs<AppSettingsResult.EmptyAppSettings>(viewModel.applyAppSettingsResult.value)

    }

    @Test
    fun applyAppSettingsResult_isAppSettingsDisabled_whenApplySettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applySettings()

        assertIs<AppSettingsResult.AppSettingsDisabled>(viewModel.applyAppSettingsResult.value)

    }

    @Test
    fun revertAppSettingsResult_isSuccess_whenRevertSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(appSettings)

        viewModel.revertSettings()

        assertIs<AppSettingsResult.Success>(viewModel.revertAppSettingsResult.value)

    }

    @Test
    fun revertAppSettingsResult_isSecurityException_whenRevertSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        secureSettingsRepository.setWriteSecureSettings(false)

        appSettingsRepository.setAppSettings(appSettings)

        viewModel.revertSettings()

        assertIs<AppSettingsResult.SecurityException>(viewModel.revertAppSettingsResult.value)

    }

    @Test
    fun revertAppSettingsResultI_isIllegalArgumentException_whenRevertSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        viewModel.revertSettings()

        assertIs<AppSettingsResult.IllegalArgumentException>(viewModel.revertAppSettingsResult.value)

    }

    @Test
    fun revertAppSettingsResult_isEmptyAppSettings_whenRevertSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        viewModel.revertSettings()

        assertIs<AppSettingsResult.EmptyAppSettings>(viewModel.revertAppSettingsResult.value)

    }

    @Test
    fun revertAppSettingsResult_isAppSettingsDisabled_whenRevertSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertSettings()

        assertIs<AppSettingsResult.AppSettingsDisabled>(viewModel.revertAppSettingsResult.value)

    }

    @Test
    fun clipboardResult_isNotify_whenCopyPermissionCommand() = runTest {
        clipboardRepository.setApi32AndHigher(false)

        viewModel.copyPermissionCommand()

        assertIs<ClipboardResult.Notify>(viewModel.clipboardResult.value)

    }

    @Test
    fun clipboardResult_isHideNotify_whenCopyPermissionCommand() = runTest {
        clipboardRepository.setApi32AndHigher(true)

        viewModel.copyPermissionCommand()

        assertIs<ClipboardResult.HideNotify>(viewModel.clipboardResult.value)

    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettings() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettings(text = "Geto", settingType = SettingType.GLOBAL)

        val item = viewModel.secureSettings.value

        assertTrue(item.isNotEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettings() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettings(text = "_", settingType = SettingType.GLOBAL)

        val item = viewModel.secureSettings.value

        assertTrue(item.isEmpty())
    }

    @Test
    fun shortcutResult_isSupportedLauncher_whenRequestPinShortcut() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(true)

        viewModel.requestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertIs<ShortcutResult.SupportedLauncher>(viewModel.shortcutResult.value)
    }

    @Test
    fun shortcutResult_isUnSupportedLauncher_whenRequestPinShortcut() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(false)

        viewModel.requestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertIs<ShortcutResult.UnsupportedLauncher>(viewModel.shortcutResult.value)
    }

    @Test
    fun shortcutResult_isShortcutUpdateImmutableShortcuts_whenUpdateRequestPinShortcut() = runTest {
        shortcutRepository.setUpdateImmutableShortcuts(true)

        viewModel.updateRequestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "Geto", longLabel = "Geto"
            )
        )

        assertIs<ShortcutResult.ShortcutUpdateImmutableShortcuts>(viewModel.shortcutResult.value)
    }

    @Test
    fun shortcutResult_isShortcutUpdateSuccess_whenUpdateRequestPinShortcut() = runTest {
        shortcutRepository.setUpdateImmutableShortcuts(false)

        viewModel.updateRequestPinShortcut(
            targetShortcutInfoCompat = TargetShortcutInfoCompat(
                id = "0", shortLabel = "Geto", longLabel = "Geto"
            )
        )

        assertIs<ShortcutResult.ShortcutUpdateSuccess>(viewModel.shortcutResult.value)
    }

    @Test
    fun shortcutResult_isGetShortcut_whenGetShortcut() = runTest {
        val shortcuts = List(2) {
            TargetShortcutInfoCompat(
                id = "com.android.geto", shortLabel = "Geto", longLabel = "Geto"
            )
        }

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.getShortcut()

        assertIs<ShortcutResult.GetShortcut>(viewModel.shortcutResult.value)
    }

    @Test
    fun shortcutResult_isNoShortcut_whenGetShortcut() = runTest {
        val shortcuts = List(2) {
            TargetShortcutInfoCompat(
                id = "com.android.geto", shortLabel = "Geto", longLabel = "Geto"
            )
        }

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.getShortcut("")

        assertIs<ShortcutResult.NoShortcut>(viewModel.shortcutResult.value)
    }

    @Test
    fun applicationIcon_isNotNull_whenGetApplicationIcon() = runTest {
        val installedApplications = List(1) { _ ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto", label = "Geto"
            )
        }

        packageRepository.setInstalledApplications(installedApplications)

        viewModel.getApplicationIcon()

        assertNotNull(viewModel.applicationIcon.value)
    }

    @Test
    fun applicationIcon_isNull_whenGetApplicationIcon() = runTest {
        val installedApplications = List(1) { _ ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto", label = "Geto"
            )
        }

        packageRepository.setInstalledApplications(installedApplications)

        viewModel.getApplicationIcon("")

        assertNull(viewModel.applicationIcon.value)
    }
}