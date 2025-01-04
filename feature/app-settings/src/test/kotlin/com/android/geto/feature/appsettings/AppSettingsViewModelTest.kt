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
import androidx.navigation.testing.invoke
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver
import com.android.geto.core.domain.model.AddAppSettingResult
import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.model.AppSettingTemplate
import com.android.geto.core.domain.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.InvalidValues
import com.android.geto.core.domain.model.AppSettingsResult.NoPermission
import com.android.geto.core.domain.model.AppSettingsResult.Success
import com.android.geto.core.domain.model.GetoApplicationInfo
import com.android.geto.core.domain.model.GetoShortcutInfoCompat
import com.android.geto.core.domain.model.RequestPinShortcutResult
import com.android.geto.core.domain.model.SecureSetting
import com.android.geto.core.domain.model.SettingType
import com.android.geto.core.domain.usecase.AddAppSettingUseCase
import com.android.geto.core.domain.usecase.ApplyAppSettingsUseCase
import com.android.geto.core.domain.usecase.AutoLaunchUseCase
import com.android.geto.core.domain.usecase.RequestPinShortcutUseCase
import com.android.geto.core.domain.usecase.RevertAppSettingsUseCase
import com.android.geto.core.domain.broadcastreceiver.DummyRevertSettingsBroadcastReceiver
import com.android.geto.core.domain.framework.DummyClipboardManagerWrapper
import com.android.geto.core.domain.framework.DummyNotificationManagerWrapper
import com.android.geto.core.domain.framework.FakeAssetManagerWrapper
import com.android.geto.core.domain.framework.FakePackageManagerWrapper
import com.android.geto.core.domain.repository.TestAppSettingsRepository
import com.android.geto.core.domain.repository.TestSecureSettingsRepository
import com.android.geto.core.domain.repository.TestShortcutRepository
import com.android.geto.core.domain.repository.TestUserDataRepository
import com.android.geto.core.common.MainDispatcherRule
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
import com.android.geto.feature.appsettings.navigation.AppSettingsRouteData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: FakePackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardManagerWrapper: DummyClipboardManagerWrapper

    private lateinit var notificationManagerWrapper: DummyNotificationManagerWrapper

    private lateinit var assetManagerWrapper: FakeAssetManagerWrapper

    private lateinit var shortcutRepository: TestShortcutRepository

    private lateinit var userDataRepository: TestUserDataRepository

    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var autoLaunchUseCase: AutoLaunchUseCase

    private lateinit var addAppSettingUseCase: AddAppSettingUseCase

    private lateinit var requestPinShortcutUseCase: RequestPinShortcutUseCase

    private lateinit var revertSettingsBroadcastReceiver: RevertSettingsBroadcastReceiver

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: AppSettingsViewModel

    private val packageName = "com.android.geto"

    private val appName = "Geto"

    @Before
    fun setup() {
        packageManagerWrapper = FakePackageManagerWrapper()

        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        clipboardManagerWrapper = DummyClipboardManagerWrapper()

        shortcutRepository = TestShortcutRepository()

        userDataRepository = TestUserDataRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        autoLaunchUseCase = AutoLaunchUseCase(
            userDataRepository = userDataRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        requestPinShortcutUseCase =
            RequestPinShortcutUseCase(shortcutRepository = shortcutRepository)

        addAppSettingUseCase = AddAppSettingUseCase(
            appSettingsRepository = appSettingsRepository,
        )

        savedStateHandle = SavedStateHandle(
            route = AppSettingsRouteData(
                packageName = packageName,
                appName = appName,
            ),
        )

        notificationManagerWrapper = DummyNotificationManagerWrapper()

        assetManagerWrapper = FakeAssetManagerWrapper()

        revertSettingsBroadcastReceiver = DummyRevertSettingsBroadcastReceiver()

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            packageManagerWrapper = packageManagerWrapper,
            clipboardManagerWrapper = clipboardManagerWrapper,
            secureSettingsRepository = secureSettingsRepository,
            applyAppSettingsUseCase = applyAppSettingsUseCase,
            revertAppSettingsUseCase = revertAppSettingsUseCase,
            autoLaunchUseCase = autoLaunchUseCase,
            requestPinShortcutUseCase = requestPinShortcutUseCase,
            addAppSettingUseCase = addAppSettingUseCase,
            notificationManagerWrapper = notificationManagerWrapper,
            assetManagerWrapper = assetManagerWrapper,
            revertSettingsBroadcastReceiver = revertSettingsBroadcastReceiver,
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
    fun applicationIcon_isNull_whenStarted() {
        assertNull(viewModel.applicationIcon.value)
    }

    @Test
    fun templateDialogUiState_isLoading_whenStarted() {
        assertIs<TemplateDialogUiState.Loading>(viewModel.templateDialogUiState.value)
    }

    @Test
    fun addAppSettingResult_isNull_whenStarted() {
        assertNull(viewModel.addAppSettingsResult.value)
    }

    @Test
    fun appSettingsUiState_isSuccess_whenAppSettings_isNotEmpty() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.appSettingsUiState.collect()
        }

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

        assertIs<AppSettingsUiState.Success>(viewModel.appSettingsUiState.value)
    }

    @Test
    fun templateDialogUiState_isSuccess_whenAppSettingTemplates_isNotEmpty() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            val appSettingTemplates = List(5) { index ->
                AppSettingTemplate(
                    settingType = SettingType.SYSTEM,
                    label = "Geto",
                    key = "Geto $index",
                    valueOnLaunch = "0",
                    valueOnRevert = "1",
                )
            }

            assetManagerWrapper.setAppSettingTemplates(appSettingTemplates)

            viewModel.templateDialogUiState.collect()
        }

        assertIs<TemplateDialogUiState.Success>(viewModel.templateDialogUiState.value)
    }

    @Test
    fun applyAppSettingsResult_isSuccess_whenApplyAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.applyAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.ApplyAppSettings)

        assertEquals(
            expected = Success,
            actual = viewModel.applyAppSettingsResult.value,
        )
    }

    @Test
    fun applyAppSettingsResult_isNoPermission_whenApplyAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.applyAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.ApplyAppSettings)

        assertEquals(
            expected = NoPermission,
            actual = viewModel.applyAppSettingsResult.value,
        )
    }

    @Test
    fun applyAppSettingsResult_isInvalidValues_whenApplyAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.applyAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.ApplyAppSettings)

        assertEquals(
            expected = InvalidValues,
            actual = viewModel.applyAppSettingsResult.value,
        )
    }

    @Test
    fun applyAppSettingsResult_isEmptyAppSettings_whenApplyAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.applyAppSettingsResult.collect()
        }

        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.onEvent(event = AppSettingsEvent.ApplyAppSettings)

        assertEquals(
            expected = EmptyAppSettings,
            actual = viewModel.applyAppSettingsResult.value,
        )
    }

    @Test
    fun applyAppSettingsResult_isDisabledAppSettings_whenApplyAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.applyAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.ApplyAppSettings)

        assertEquals(
            expected = DisabledAppSettings,
            actual = viewModel.applyAppSettingsResult.value,
        )
    }

    @Test
    fun revertAppSettingsResult_isSuccess_whenRevertAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.RevertAppSettings)

        assertEquals(
            expected = Success,
            actual = viewModel.revertAppSettingsResult.value,
        )
    }

    @Test
    fun revertAppSettingsResult_isNoPermission_whenRevertAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.RevertAppSettings)

        assertEquals(
            expected = NoPermission,
            actual = viewModel.revertAppSettingsResult.value,
        )
    }

    @Test
    fun revertAppSettingsResultI_isInvalidValues_whenRevertAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.RevertAppSettings)

        assertEquals(
            expected = InvalidValues,
            actual = viewModel.revertAppSettingsResult.value,
        )
    }

    @Test
    fun revertAppSettingsResult_isEmptyAppSettings_whenRevertAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

        appSettingsRepository.setAppSettings(emptyList())

        viewModel.onEvent(event = AppSettingsEvent.RevertAppSettings)

        assertEquals(
            expected = EmptyAppSettings,
            actual = viewModel.revertAppSettingsResult.value,
        )
    }

    @Test
    fun revertAppSettingsResult_isDisabledAppSettings_whenRevertAppSettings() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

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

        viewModel.onEvent(event = AppSettingsEvent.RevertAppSettings)

        assertEquals(
            expected = DisabledAppSettings,
            actual = viewModel.revertAppSettingsResult.value,
        )
    }

    @Test
    fun setPrimaryClipResult_isFalse_whenCopyCommand() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.revertAppSettingsResult.collect()
        }

        clipboardManagerWrapper.setSDKInt(33)

        viewModel.onEvent(
            event = AppSettingsEvent.CopyCommand(
                label = "label",
                text = "text",
            ),
        )

        assertEquals(
            expected = false,
            actual = viewModel.setPrimaryClipResult.value,
        )
    }

    @Test
    fun setPrimaryClipResult_isTrue_whenCopyCommand() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.setPrimaryClipResult.collect()
        }

        clipboardManagerWrapper.setSDKInt(32)

        viewModel.onEvent(
            event = AppSettingsEvent.CopyCommand(
                label = "label",
                text = "text",
            ),
        )

        assertTrue(viewModel.setPrimaryClipResult.value)
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeSystem() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.SYSTEM,
                text = "SecureSetting",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeSecure() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SECURE,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.SECURE,
                text = "SecureSetting",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isNotEmpty_whenGetSecureSettingsByName_ofSettingTypeGlobal() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.GLOBAL,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.GLOBAL,
                text = "SecureSetting",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isNotEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeSystem() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.SYSTEM,
                text = "text",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeSecure() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SECURE,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.SECURE,
                text = "text",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun secureSettings_isEmpty_whenGetSecureSettingsByName_ofSettingTypeGlobal() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.secureSettings.collect()
        }

        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.GLOBAL,
                id = index.toLong(),
                name = "SecureSetting",
                value = index.toString(),
            )
        }

        secureSettingsRepository.setSecureSettings(secureSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.GetSecureSettingsByName(
                settingType = SettingType.GLOBAL,
                text = "text",
            ),
        )

        assertTrue(viewModel.secureSettings.value.isEmpty())
    }

    @Test
    fun requestPinShortcutResult_isSupportedLauncher_whenRequestPinShortcut() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        viewModel.onEvent(
            event = AppSettingsEvent.RequestPinShortcut(
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "0",
                    icon = ByteArray(0),
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )

        assertEquals(
            expected = RequestPinShortcutResult.SupportedLauncher,
            actual = viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun requestPinShortcutResult_isUnsupportedLauncher_whenRequestPinShortcut() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

        shortcutRepository.setRequestPinShortcutSupported(false)

        viewModel.onEvent(
            event = AppSettingsEvent.RequestPinShortcut(
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "0",
                    icon = ByteArray(0),
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )

        assertEquals(
            expected = RequestPinShortcutResult.UnsupportedLauncher,
            actual = viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun requestPinShortcutResult_isUpdateImmutableShortcuts_whenRequestPinShortcut() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

        val shortcuts = List(2) {
            GetoShortcutInfoCompat(
                id = "com.android.geto",
                icon = ByteArray(0),
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(true)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.onEvent(
            event = AppSettingsEvent.RequestPinShortcut(
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "com.android.geto",
                    icon = ByteArray(0),
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )

        assertEquals(
            expected = RequestPinShortcutResult.UpdateImmutableShortcuts,
            actual = viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun requestPinShortcutResult_isUpdateSuccess_whenRequestPinShortcut() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

        val shortcuts = List(2) {
            GetoShortcutInfoCompat(
                id = "com.android.geto",
                icon = ByteArray(0),
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        viewModel.onEvent(
            event = AppSettingsEvent.RequestPinShortcut(
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "com.android.geto",
                    icon = ByteArray(0),
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )

        assertEquals(
            expected = RequestPinShortcutResult.UpdateSuccess,
            actual = viewModel.requestPinShortcutResult.value,
        )
    }

    @Test
    fun applicationIcon_isNotNull_whenGetApplicationIcon() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            val getoApplicationInfos = List(1) { _ ->
                GetoApplicationInfo(
                    flags = 0,
                    icon = ByteArray(0),
                    packageName = packageName,
                    label = appName,
                )
            }

            packageManagerWrapper.setApplicationInfos(getoApplicationInfos)

            viewModel.applicationIcon.collect()
        }

        assertNotNull(viewModel.applicationIcon.value)
    }

    @Test
    fun applicationIcon_isNull_whenGetApplicationIcon() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            val getoApplicationInfos = List(1) { _ ->
                GetoApplicationInfo(
                    flags = 0,
                    icon = ByteArray(0),
                    packageName = "",
                    label = appName,
                )
            }

            packageManagerWrapper.setApplicationInfos(getoApplicationInfos)

            viewModel.applicationIcon.collect()
        }

        assertNull(viewModel.applicationIcon.value)
    }

    @Test
    fun addAppSettingResult_isSuccess_whenAddAppSetting() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

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

        val newAppSetting = AppSetting(
            id = 6,
            enabled = true,
            settingType = SettingType.SYSTEM,
            packageName = packageName,
            label = "Geto",
            key = "Geto",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )

        appSettingsRepository.setAppSettings(appSettings)

        viewModel.onEvent(
            event = AppSettingsEvent.AddAppSetting(
                appSetting = newAppSetting,
            ),
        )

        assertEquals(
            expected = AddAppSettingResult.SUCCESS,
            actual = viewModel.addAppSettingsResult.value,
        )
    }

    @Test
    fun addAppSettingResult_isFailed_whenAddAppSetting() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.requestPinShortcutResult.collect()
        }

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

        viewModel.onEvent(
            event = AppSettingsEvent.AddAppSetting(
                appSetting = appSettings.first(),
            ),
        )

        assertEquals(
            expected = AddAppSettingResult.FAILED,
            actual = viewModel.addAppSettingsResult.value,
        )
    }
}
