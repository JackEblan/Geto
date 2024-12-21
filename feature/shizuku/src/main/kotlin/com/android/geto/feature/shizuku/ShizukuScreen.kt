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
package com.android.geto.feature.shizuku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.AnimatedWavyCircle
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.domain.model.ShizukuStatus

@Composable
internal fun ShizukuRoute(
    modifier: Modifier = Modifier,
    viewModel: ShizukuViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val shizukuStatus by viewModel.shizukuStatus.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ShizukuScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        shizukuStatus = shizukuStatus,
        onEvent = viewModel::onEvent,
        onNavigationIconClick = onNavigationIconClick,
    )
}

@Composable
internal fun ShizukuScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    shizukuStatus: ShizukuStatus?,
    onEvent: (ShizukuEvent) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val shizukuServiceRunning = stringResource(R.string.shizuku_service_is_unbound)

    val shizukuBound = stringResource(R.string.shizuku_is_bound)

    val shizukuGranted =
        stringResource(R.string.shizuku_permission_granted_connecting_to_user_service)

    val shizukuAliveBinder = stringResource(R.string.shizuku_alive_binder)

    val shizukuDenied = stringResource(R.string.shizuku_permission_denied)

    val shizukuUpgrade = stringResource(R.string.please_upgrade_shizuku_version)

    val writeSecureSettingsGranted = stringResource(R.string.write_secure_settings_granted)

    val remoteException = stringResource(R.string.something_went_wrong_with_the_request)

    val shizukuError = stringResource(R.string.please_check_if_shizuku_is_properly_running)

    val shizukuDeadBinder = stringResource(R.string.shizuku_dead_binder)

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                onEvent(ShizukuEvent.onCreate)
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                onEvent(ShizukuEvent.onDestroy)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = shizukuStatus) {
        when (shizukuStatus) {
            ShizukuStatus.UnBound -> {
                snackbarHostState.showSnackbar(
                    message = shizukuServiceRunning,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.Bound -> {
                snackbarHostState.showSnackbar(
                    message = shizukuBound,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.Granted -> {
                snackbarHostState.showSnackbar(
                    message = shizukuGranted,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.AliveBinder -> {
                snackbarHostState.showSnackbar(
                    message = shizukuAliveBinder,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.Denied -> {
                snackbarHostState.showSnackbar(
                    message = shizukuDenied,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.UpgradeShizuku -> {
                snackbarHostState.showSnackbar(
                    message = shizukuUpgrade,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.CanWriteSecureSettings -> {
                snackbarHostState.showSnackbar(
                    message = writeSecureSettingsGranted,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.RemoteException -> {
                snackbarHostState.showSnackbar(
                    message = remoteException,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.Error -> {
                snackbarHostState.showSnackbar(
                    message = shizukuError,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            ShizukuStatus.DeadBinder -> {
                snackbarHostState.showSnackbar(
                    message = shizukuDeadBinder,
                    duration = SnackbarDuration.Indefinite,
                )
            }

            null -> {
            }
        }
    }

    Scaffold(
        topBar = {
            ShizukuTopAppBar(
                title = stringResource(R.string.shizuku),
                onNavigationIconClick = onNavigationIconClick,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.testTag("shizuku:snackbar"),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .consumeWindowInsets(innerPadding),
        ) {
            AnimatedWavyCircle(
                modifier = modifier.fillMaxSize(),
                active = shizukuStatus == ShizukuStatus.CanWriteSecureSettings,
                onClick = {
                    onEvent(ShizukuEvent.CheckShizukuPermission)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShizukuTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, maxLines = 1)
        },
        modifier = modifier.testTag("shizuku:topAppBar"),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back,
                    contentDescription = "Navigation icon",
                )
            }
        },
    )
}
