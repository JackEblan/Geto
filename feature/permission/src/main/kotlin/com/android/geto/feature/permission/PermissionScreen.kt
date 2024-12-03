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

package com.android.geto.feature.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.AnimatedWavyCircle
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.domain.model.ShizukuStatus

@Composable
internal fun PermissionRoute(
    modifier: Modifier = Modifier,
    viewModel: PermissionViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val shizukuStatus by viewModel.shizukuStatus.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    PermissionScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        shizukuStatus = shizukuStatus,
        onEvent = viewModel::onEvent,
        onNavigationIconClick = onNavigationIconClick,
    )
}

@Composable
internal fun PermissionScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    shizukuStatus: ShizukuStatus,
    onEvent: (PermissionEvent) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    var isBound by rememberSaveable { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                onEvent(PermissionEvent.OnCreate)
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                onEvent(PermissionEvent.OnDestroy)
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
                isBound = false
            }

            ShizukuStatus.Bound -> {
            }

            ShizukuStatus.Granted -> {
                snackbarHostState.showSnackbar(message = "Shizuku Permission Granted")
            }

            ShizukuStatus.Denied -> {
                snackbarHostState.showSnackbar(message = "Shizuku Permission Denied")
            }

            ShizukuStatus.UpgradeShizuku -> {
                snackbarHostState.showSnackbar(message = "Please upgrade Shizuku version")
            }

            ShizukuStatus.CanWriteSecureSettings -> {
                isBound = true

                snackbarHostState.showSnackbar(message = "Write Secure Settings granted")
            }

            ShizukuStatus.RemoteException -> {
                snackbarHostState.showSnackbar(message = "Something went wrong")
            }

            ShizukuStatus.DeadBinder -> {
                snackbarHostState.showSnackbar(message = "Shizuku Service not running")
            }

            ShizukuStatus.AliveBinder -> {
                snackbarHostState.showSnackbar(message = "Shizuku Service running")
            }

            ShizukuStatus.Loading -> {
                snackbarHostState.showSnackbar(message = "Connecting to Shizuku")
            }
        }
    }

    Scaffold(
        topBar = {
            AppSettingsTopAppBar(
                title = "Permission",
                onNavigationIconClick = onNavigationIconClick,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.testTag("appSettings:snackbar"),
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
                active = isBound,
                onClick = {
                    onEvent(PermissionEvent.CheckShizukuPermission)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, maxLines = 1)
        },
        modifier = modifier.testTag("permission:topAppBar"),
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