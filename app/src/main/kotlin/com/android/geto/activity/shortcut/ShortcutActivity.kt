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
package com.android.geto.activity.shortcut

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.geto.broadcastreceiver.RevertSettingsBroadcastReceiver
import com.android.geto.domain.framework.ShortcutManagerCompatWrapper
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.framework.launcherapps.AndroidLauncherAppsWrapper
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.ACTION_REVERT_SETTINGS
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.NOTIFICATION_EXTRA_COMPONENT_NAME
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.NOTIFICATION_EXTRA_NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShortcutActivity : ComponentActivity() {
    @Inject
    lateinit var androidNotificationManagerWrapper: AndroidNotificationManagerWrapper

    @Inject
    lateinit var androidLauncherAppsWrapper: AndroidLauncherAppsWrapper

    private val viewModel: ShortcutActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val componentName =
            intent.getStringExtra(ShortcutManagerCompatWrapper.SHORTCUT_EXTRA_COMPONENT_NAME)
                ?: return

        val notificationId = componentName.hashCode()

        viewModel.applyAppSettings(componentName = componentName)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shortcutActivityUiState.collect { shortcutActivityUiState ->
                    if (shortcutActivityUiState is ShortcutActivityUiState.Success) {
                        when (shortcutActivityUiState.appSettingsResult) {
                            AppSettingsResult.Success -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.apply_success),
                                    ),
                                )

                                androidLauncherAppsWrapper.startMainActivity(componentName = componentName)

                                finish()
                            }

                            AppSettingsResult.Failure -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.apply_failure),
                                    ),
                                )

                                finish()
                            }

                            AppSettingsResult.NoPermission -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.no_permission),
                                    ),
                                )

                                finish()
                            }

                            AppSettingsResult.InvalidValues -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.settings_has_invalid_values),
                                    ),
                                )

                                finish()
                            }

                            AppSettingsResult.EmptyAppSettings -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.empty_app_settings_list),
                                    ),
                                )

                                finish()
                            }

                            AppSettingsResult.DisabledAppSettings -> {
                                androidNotificationManagerWrapper.notify(
                                    id = notificationId,
                                    notification = getNotification(
                                        notificationId = notificationId,
                                        componentName = componentName,
                                        icon = shortcutActivityUiState.applicationIcon,
                                        contentTitle = getString(com.android.geto.feature.appsettings.R.string.geto_settings),
                                        contentText = getString(com.android.geto.feature.appsettings.R.string.app_settings_disabled),
                                    ),
                                )

                                finish()
                            }

                            null -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun getNotification(
        notificationId: Int,
        componentName: String,
        icon: ByteArray?,
        contentTitle: String,
        contentText: String,
    ): Notification {
        val revertIntent = Intent(this, RevertSettingsBroadcastReceiver::class.java).apply {
            action = ACTION_REVERT_SETTINGS
            putExtra(NOTIFICATION_EXTRA_COMPONENT_NAME, componentName)
            putExtra(NOTIFICATION_EXTRA_NOTIFICATION_ID, notificationId)
        }

        val revertPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            revertIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(
            this,
            AndroidNotificationManagerWrapper.NOTIFICATION_CHANNEL_ID,
        ).apply {
            setSmallIcon(com.android.geto.framework.notificationmanager.R.drawable.baseline_settings_24)

            icon?.let {
                setLargeIcon(Icon.createWithData(icon, 0, it.size))
            }

            setContentTitle(contentTitle)
            setContentText(contentText)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            addAction(
                com.android.geto.framework.notificationmanager.R.drawable.baseline_settings_24,
                getString(com.android.geto.framework.notificationmanager.R.string.revert),
                revertPendingIntent,
            )
        }.build()
    }
}
