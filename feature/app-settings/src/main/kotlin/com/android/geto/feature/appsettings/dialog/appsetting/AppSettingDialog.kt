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
package com.android.geto.feature.appsettings.dialog.appsetting

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.R

@Composable
internal fun AppSettingDialog(
    modifier: Modifier = Modifier,
    appSettingDialogState: AppSettingDialogState,
    scrollState: ScrollState = rememberScrollState(),
    packageName: String,
    onAddClick: (AppSetting) -> Unit,
    contentDescription: String,
) {
    DialogContainer(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = { appSettingDialogState.updateShowDialog(false) },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            AppSettingDialogTitle()

            AppSettingDialogRadioButtonGroup(
                selected = appSettingDialogState.selectedRadioOptionIndex,
                onSelect = appSettingDialogState::updateSelectedRadioOptionIndex,
            )

            AppSettingDialogTextFields(
                appSettingDialogState = appSettingDialogState,
            )

            AppSettingDialogButtons(
                onCancelClick = {
                    appSettingDialogState.updateShowDialog(false)
                },
                onAddClick = {
                    appSettingDialogState.getAppSetting(packageName = packageName)?.let {
                        onAddClick(it)
                        appSettingDialogState.resetState()
                    }
                },
            )
        }
    }
}

@Composable
private fun AppSettingDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(10.dp),
        text = stringResource(R.string.add_app_setting),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun AppSettingDialogRadioButtonGroup(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        SettingType.entries.map { it.label }.toTypedArray().forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = index == selected,
                        role = Role.RadioButton,
                        enabled = true,
                        onClick = {
                            onSelect(index)
                        },
                    )
                    .padding(horizontal = 16.dp)
                    .semantics { contentDescription = text },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = index == selected,
                    onClick = null,
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }
    }
}

@Composable
private fun AppSettingDialogTextFields(
    appSettingDialogState: AppSettingDialogState,
) {
    val labelIsBlank = stringResource(id = R.string.setting_label_is_blank)
    val valueOnLaunchIsBlank = stringResource(id = R.string.setting_value_on_launch_is_blank)
    val valueOnRevertIsBlank = stringResource(id = R.string.setting_value_on_revert_is_blank)

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .testTag("appSettingDialog:labelTextField"),
        value = appSettingDialogState.label,
        onValueChange = appSettingDialogState::updateLabel,
        label = {
            Text(text = stringResource(R.string.setting_label))
        },
        isError = appSettingDialogState.showLabelError,
        supportingText = {
            if (appSettingDialogState.showLabelError) {
                Text(
                    text = labelIsBlank,
                    modifier = Modifier.testTag("appSettingDialog:labelSupportingText"),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    AppSettingDialogTextFieldWithDropdownMenu(
        appSettingDialogState = appSettingDialogState,
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .testTag("appSettingDialog:valueOnLaunchTextField"),
        value = appSettingDialogState.valueOnLaunch,
        onValueChange = appSettingDialogState::updateValueOnLaunch,
        label = {
            Text(text = stringResource(R.string.setting_value_on_launch))
        },
        isError = appSettingDialogState.showValueOnLaunchError,
        supportingText = {
            if (appSettingDialogState.showValueOnLaunchError) {
                Text(
                    text = valueOnLaunchIsBlank,
                    modifier = Modifier.testTag("appSettingDialog:valueOnLaunchSupportingText"),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .testTag("appSettingDialog:valueOnRevertTextField"),
        value = appSettingDialogState.valueOnRevert,
        onValueChange = appSettingDialogState::updateValueOnRevert,
        label = {
            Text(text = stringResource(R.string.setting_value_on_revert))
        },
        isError = appSettingDialogState.showValueOnRevertError,
        supportingText = {
            if (appSettingDialogState.showValueOnRevertError) {
                Text(
                    text = valueOnRevertIsBlank,
                    modifier = Modifier.testTag("appSettingDialog:valueOnRevertSupportingText"),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingDialogTextFieldWithDropdownMenu(
    appSettingDialogState: AppSettingDialogState,
) {
    val keyIsBlank = stringResource(id = R.string.setting_key_is_blank)
    val keyNotFound = stringResource(id = R.string.setting_key_not_found)

    ExposedDropdownMenuBox(
        expanded = appSettingDialogState.secureSettingsExpanded,
        onExpandedChange = appSettingDialogState::updateSecureSettingsExpanded,
        modifier = Modifier.testTag("appSettingDialog:exposedDropdownMenuBox"),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(
                    type = MenuAnchorType.SecondaryEditable,
                    enabled = true,
                )
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .testTag("appSettingDialog:keyTextField"),
            value = appSettingDialogState.key,
            onValueChange = appSettingDialogState::updateKey,
            label = {
                Text(text = stringResource(R.string.setting_key))
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = appSettingDialogState.secureSettingsExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            isError = appSettingDialogState.showKeyError || appSettingDialogState.showKeyNotFoundError,
            supportingText = {
                if (appSettingDialogState.showKeyError) {
                    Text(
                        text = keyIsBlank,
                        modifier = Modifier.testTag("appSettingDialog:keySupportingText"),
                    )
                }

                if (appSettingDialogState.showKeyNotFoundError) {
                    Text(
                        text = keyNotFound,
                        modifier = Modifier.testTag("appSettingDialog:settingsKeyNotFoundSupportingText"),
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        if (appSettingDialogState.secureSettings.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = appSettingDialogState.secureSettingsExpanded,
                onDismissRequest = {
                    appSettingDialogState.updateSecureSettingsExpanded(false)
                },
            ) {
                appSettingDialogState.secureSettings.forEach { secureSetting ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = secureSetting.name ?: "null",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        onClick = {
                            appSettingDialogState.updateKey(
                                secureSetting.name ?: "null",
                            )

                            appSettingDialogState.updateValueOnRevert(
                                secureSetting.value ?: "null",
                            )

                            appSettingDialogState.updateSecureSettingsExpanded(
                                false,
                            )
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Composable
private fun AppSettingDialogButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancelClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = stringResource(R.string.cancel))
        }
        TextButton(
            onClick = onAddClick,
            modifier = Modifier
                .padding(5.dp)
                .testTag("Test Add"),
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}
