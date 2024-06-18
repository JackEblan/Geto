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

import com.android.geto.core.domain.ApplyAppSettingsResult
import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.domain.AutoLaunchUseCase
import com.android.geto.core.domain.GetPinnedShortcutResult
import com.android.geto.core.domain.GetPinnedShortcutUseCase
import com.android.geto.core.domain.RequestPinShortcutResult
import com.android.geto.core.domain.RequestPinShortcutUseCase
import com.android.geto.core.domain.RevertAppSettingsResult
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.domain.UpdateRequestPinShortcutResult
import com.android.geto.core.domain.UpdateRequestPinShortcutUseCase
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.MappedApplicationInfo
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestClipboardRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.repository.TestShortcutRepository
import com.android.geto.core.testing.repository.TestUserDataRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

    private lateinit var userDataRepository: TestUserDataRepository

    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var autoLaunchUseCase: AutoLaunchUseCase

    private lateinit var requestPinShortcutUseCase: RequestPinShortcutUseCase

    private lateinit var updateRequestPinShortcutUseCase: UpdateRequestPinShortcutUseCase

    private lateinit var getPinnedShortcutUseCase: GetPinnedShortcutUseCase

    private lateinit var viewModel: AppSettingsViewModel

    private val packageName = "com.android.geto"

    private val appName = "Geto"

    @Before
    fun setup() {
        packageRepository = TestPackageRepository()

        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        shortcutRepository = TestShortcutRepository()

        userDataRepository = TestUserDataRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            packageRepository = packageRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        autoLaunchUseCase = AutoLaunchUseCase(
            packageRepository = packageRepository,
            userDataRepository = userDataRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,

        )

        requestPinShortcutUseCase =
            RequestPinShortcutUseCase(shortcutRepository = shortcutRepository)

        updateRequestPinShortcutUseCase =
            UpdateRequestPinShortcutUseCase(shortcutRepository = shortcutRepository)

        getPinnedShortcutUseCase = GetPinnedShortcutUseCase(shortcutRepository = shortcutRepository)

        viewModel = AppSettingsViewModel(
            appSettingsRepository = appSettingsRepository,
            packageRepository = packageRepository,
            clipboardRepository = clipboardRepository,
            secureSettingsRepository = secureSettingsRepository,
            applyAppSettingsUseCase = applyAppSettingsUseCase,
            revertAppSettingsUseCase = revertAppSettingsUseCase,
            autoLaunchUseCase = autoLaunchUseCase,
            requestPinShortcutUseCase = requestPinShortcutUseCase,
            updateRequestPinShortcutUseCase = updateRequestPinShortcutUseCase,
            getPinnedShortcutUseCase = getPinnedShortcutUseCase,
        )
    }

    @Test
    fun appSettingsUiState_isLoading_whenStarted() {
        assertIs<AppSettingsUiState.Loading>(viewModel.appSettingsUiState.value)
    }

    @Test
    fun applyAppSettingsResult_isNull_whenStarted() {
        assertNull(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResult_isNull_whenStarted() {
        assertNull(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun autoLaunchResult_isNull_whenStarted() {
        assertNull(viewModel.autoLaunchResult.value)
    }

    @Test
    fun setPrimaryClipResult_isFalse_whenStarted() {
        assertFalse(viewModel.setPrimaryClipResult.value)
    }

    @Test
    fun secureSettings_isEmpty_whenStarted() {
        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun requestPinShortcutResult_isNull_whenStarted() {
        assertNull(viewModel.requestPinShortcutResult.value)
    }

    @Test
    fun updateRequestPinShortcutResult_isNull_whenStarted() {
        assertNull(viewModel.updateRequestPinShortcutResult.value)
    }

    @Test
    fun applicationIcon_isNull_whenStarted() {
        assertNull(viewModel.applicationIcon.value)
    }

    @Test
    fun getPinnedShortcutResult_isNull_whenStarted() {
        assertNull(viewModel.getPinnedShortcutResult.value)
    }

    @Test
    fun appSettingsUiState_isSuccess_whenAppSettings_isNotEmpty() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        viewModel.updatePackageName(packageName = packageName)

        appSettingsRepository.setAppSettings(appSettings)

        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.appSettingsUiState.collect()
        }

        assertIs<AppSettingsUiState.Success>(viewModel.appSettingsUiState.value)

        collectJob.cancel()
    }

    @Test
    fun applyAppSettingsResult_isSuccess_whenApplyAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applyAppSettings(packageName = packageName)

        assertIs<ApplyAppSettingsResult.Success>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun applyAppSettingsResult_isSecurityException_whenApplyAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(false)

        viewModel.applyAppSettings(packageName = packageName)

        assertIs<ApplyAppSettingsResult.SecurityException>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun applyAppSettingsResult_isIllegalArgumentException_whenApplyAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        viewModel.applyAppSettings(packageName = packageName)

        assertIs<ApplyAppSettingsResult.IllegalArgumentException>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun applyAppSettingsResult_isEmptyAppSettings_whenApplyAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applyAppSettings(packageName = packageName)

        assertIs<ApplyAppSettingsResult.EmptyAppSettings>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun applyAppSettingsResult_isDisabledAppSettings_whenApplyAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.applyAppSettings(packageName = packageName)

        assertIs<ApplyAppSettingsResult.DisabledAppSettings>(viewModel.applyAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResult_isSuccess_whenRevertAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(appSettings)

        viewModel.revertAppSettings(packageName = packageName)

        assertIs<RevertAppSettingsResult.Success>(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResult_isSecurityException_whenRevertAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        secureSettingsRepository.setWriteSecureSettings(false)

        appSettingsRepository.setAppSettings(appSettings)

        viewModel.revertAppSettings(packageName = packageName)

        assertIs<RevertAppSettingsResult.SecurityException>(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResultI_isIllegalArgumentException_whenRevertAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        viewModel.revertAppSettings(packageName = packageName)

        assertIs<RevertAppSettingsResult.IllegalArgumentException>(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResult_isEmptyAppSettings_whenRevertAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        viewModel.revertAppSettings(packageName = packageName)

        assertIs<RevertAppSettingsResult.EmptyAppSettings>(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun revertAppSettingsResult_isDisabledAppSettings_whenRevertAppSettings() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertAppSettings(packageName = packageName)

        assertIs<RevertAppSettingsResult.DisabledAppSettings>(viewModel.revertAppSettingsResult.value)
    }

    @Test
    fun setPrimaryClipResult_isFalse_whenCopyPermissionCommand() = runTest {
        clipboardRepository.setSDKInt(33)

        viewModel.copyPermissionCommand()

        assertEquals(
            expected = false,
            actual = viewModel.setPrimaryClipResult.value,
        )
    }

    @Test
    fun setPrimaryClipResult_isTrue_whenCopyPermissionCommand() = runTest {
        clipboardRepository.setSDKInt(32)

        viewModel.copyPermissionCommand()

        assertEquals(
            expected = true,
            actual = viewModel.setPrimaryClipResult.value,
        )
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeSystem() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.SYSTEM, text = "SecureSetting")

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeSecure() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SECURE,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.SECURE, text = "SecureSetting")

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeGlobal() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.GLOBAL,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.GLOBAL, text = "SecureSetting")

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeSystem() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.SYSTEM, text = "text")

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeSecure() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SECURE,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.SECURE, text = "text")

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeGlobal() = runTest {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.GLOBAL,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.getSecureSettingsByName(settingType = SettingType.GLOBAL, text = "text")

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun requestPinShortcutResult_isSupportedLauncher_whenRequestPinShortcut() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(true)

        viewModel.requestPinShortcut(
            packageName = packageName,
            appName = appName,
            mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                id = "0",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
            ),
        )

        assertIs<RequestPinShortcutResult.SupportedLauncher>(
            viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun requestPinShortcutResult_isUnSupportedLauncher_whenRequestPinShortcut() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(false)

        viewModel.requestPinShortcut(
            packageName = packageName,
            appName = appName,
            mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                id = "0",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
            ),
        )

        assertIs<RequestPinShortcutResult.UnSupportedLauncher>(
            viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun updateRequestPinShortcutResult_isIDNotFound_whenUpdateRequestPinShortcut() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.updateRequestPinShortcut(
            packageName = packageName,
            appName = appName,
            mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                id = "0",
                shortLabel = "",
                longLabel = "",
            ),
        )

        assertIs<UpdateRequestPinShortcutResult.IDNotFound>(
            viewModel.updateRequestPinShortcutResult.value,
        )
    }

    @Test
    fun updateRequestPinShortcutResult_isUpdateImmutableShortcuts_whenUpdateRequestPinShortcut() =
        runTest {
            val shortcuts = List(2) {
                MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                )
            }

            shortcutRepository.setUpdateImmutableShortcuts(true)

            shortcutRepository.setShortcuts(shortcuts)

            viewModel.updateRequestPinShortcut(
                packageName = packageName,
                appName = appName,
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "",
                    longLabel = "",
                ),
            )

            assertIs<UpdateRequestPinShortcutResult.UpdateImmutableShortcuts>(
                viewModel.updateRequestPinShortcutResult.value,
            )
        }

    @Test
    fun updateRequestPinShortcutResult_isSuccess_whenUpdateRequestPinShortcut() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.updateRequestPinShortcut(
            packageName = packageName,
            appName = appName,
            mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "",
                longLabel = "",
            ),
        )

        assertIs<UpdateRequestPinShortcutResult.Success>(
            viewModel.updateRequestPinShortcutResult.value,
        )
    }

    @Test
    fun getPinnedShortcutResult_isSuccess_whenGetShortcut() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.getPinnedShortcut(packageName = packageName)

        assertIs<GetPinnedShortcutResult.Success>(viewModel.getPinnedShortcutResult.value)
    }

    @Test
    fun getPinnedShortcutResult_isFailure_whenGetShortcut() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.getPinnedShortcut("")

        assertIs<GetPinnedShortcutResult.Failure>(viewModel.getPinnedShortcutResult.value)
    }

    @Test
    fun applicationIcon_isNotNull_whenGetApplicationIcon() = runTest {
        val mappedApplicationInfos = List(1) { _ ->
            MappedApplicationInfo(
                flags = 0,
                packageName = packageName,
                label = appName,
            )
        }

        packageRepository.setMappedApplicationInfos(mappedApplicationInfos)

        viewModel.getApplicationIcon(packageName = packageName)

        assertNotNull(viewModel.applicationIcon.value)
    }

    @Test
    fun applicationIcon_isNull_whenGetApplicationIcon() = runTest {
        val mappedApplicationInfos = List(1) { _ ->
            MappedApplicationInfo(
                flags = 0,
                packageName = packageName,
                label = appName,
            )
        }

        packageRepository.setMappedApplicationInfos(mappedApplicationInfos)

        viewModel.getApplicationIcon(packageName = "")

        assertNull(viewModel.applicationIcon.value)
    }
}
