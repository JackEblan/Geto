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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogButtons
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.core.designsystem.component.DialogTitle
import com.android.geto.core.designsystem.component.GetoDropdownMenuItem
import com.android.geto.core.designsystem.component.GetoOutlinedTextField
import com.android.geto.core.designsystem.component.GetoOutlinedTextFieldWithExposedDropdownMenuBox
import com.android.geto.core.designsystem.component.GetoRadioButtonGroup
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.R

@Composable
internal fun AppSettingDialog(
    modifier: Modifier = Modifier,
    appSettingDialogState: AppSettingDialogState,
    scrollState: ScrollState = rememberScrollState(),
    packageName: String,
    onAddSetting: (AppSetting) -> Unit,
    contentDescription: String,
) {
    DialogContainer(
        modifier = modifier,
        onDismissRequest = { appSettingDialogState.updateShowDialog(false) },
        contentDescription = contentDescription,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(10.dp),
        ) {
            DialogTitle(title = stringResource(R.string.add_app_setting))

            GetoRadioButtonGroup(
                selected = appSettingDialogState.selectedRadioOptionIndex,
                onSelect = appSettingDialogState::updateSelectedRadioOptionIndex,
                items = SettingType.entries.map { it.label }.toTypedArray(),
            )

            AppSettingDialogTextFields(
                appSettingDialogState = appSettingDialogState,
            )

            DialogButtons(
                negativeButtonText = stringResource(R.string.cancel),
                positiveButtonText = stringResource(R.string.add),
                onNegativeButtonClick = {
                    appSettingDialogState.updateShowDialog(false)
                },
                onPositiveButtonClick = {
                    appSettingDialogState.getAppSetting(packageName = packageName)?.let {
                        onAddSetting(it)
                        appSettingDialogState.resetState()
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingDialogTextFields(
    appSettingDialogState: AppSettingDialogState,
) {
    val labelIsBlank = stringResource(id = R.string.setting_label_is_blank)
    val keyIsBlank = stringResource(id = R.string.setting_key_is_blank)
    val keyNotFound = stringResource(id = R.string.setting_key_not_found)
    val valueOnLaunchIsBlank = stringResource(id = R.string.setting_value_on_launch_is_blank)
    val valueOnRevertIsBlank = stringResource(id = R.string.setting_value_on_revert_is_blank)

    Spacer(modifier = Modifier.height(10.dp))

    GetoOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
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

    GetoOutlinedTextFieldWithExposedDropdownMenuBox(
        exposedDropdownMenuBoxExpanded = appSettingDialogState.secureSettingsExpanded,
        onExposedDropdownMenuBoxExpandedChange = appSettingDialogState::updateSecureSettingsExpanded,
        modifier = Modifier.testTag("appSettingDialog:exposedDropdownMenuBox"),
        textFieldModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
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
        showExposedDropdownMenu = appSettingDialogState.secureSettings.isNotEmpty(),
        exposedDropdownMenuExpanded = appSettingDialogState.secureSettingsExpanded,
        onDismissRequest = {
            appSettingDialogState.updateSecureSettingsExpanded(false)
        },
        exposedDropdownMenuContent = {
            appSettingDialogState.secureSettings.forEach { secureSetting ->
                GetoDropdownMenuItem(
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
        },
    )

    GetoOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
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

    GetoOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
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

@Preview
@Composable
private fun AppSettingDialogPreview() {
    GetoTheme {
        AppSettingDialog(
            appSettingDialogState = rememberAppSettingDialogState(),
            packageName = "com.android.geto",
            onAddSetting = { },
            contentDescription = "App setting dialog",
        )
    }
}
