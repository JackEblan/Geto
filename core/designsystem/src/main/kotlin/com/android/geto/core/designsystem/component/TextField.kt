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
package com.android.geto.core.designsystem.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun GetoOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label:
    @Composable()
    (() -> Unit)? = null,
    placeholder:
    @Composable()
    (() -> Unit)? = null,
    leadingIcon:
    @Composable()
    (() -> Unit)? = null,
    trailingIcon:
    @Composable()
    (() -> Unit)? = null,
    prefix:
    @Composable()
    (() -> Unit)? = null,
    suffix:
    @Composable()
    (() -> Unit)? = null,
    supportingText:
    @Composable()
    (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetoOutlinedTextFieldWithExposedDropdownMenuBox(
    exposedDropdownMenuBoxExpanded: Boolean,
    onExposedDropdownMenuBoxExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    textFieldModifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label:
    @Composable()
    (() -> Unit)? = null,
    placeholder:
    @Composable()
    (() -> Unit)? = null,
    leadingIcon:
    @Composable()
    (() -> Unit)? = null,
    trailingIcon:
    @Composable()
    (() -> Unit)? = null,
    prefix:
    @Composable()
    (() -> Unit)? = null,
    suffix:
    @Composable()
    (() -> Unit)? = null,
    supportingText:
    @Composable()
    (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    showExposedDropdownMenu: Boolean = false,
    exposedDropdownMenuExpanded: Boolean,
    onDismissRequest: () -> Unit,
    exposedDropdownMenuModifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    exposedDropdownMenuContent:
    @Composable()
    (ColumnScope.() -> Unit),
) {
    ExposedDropdownMenuBox(
        expanded = exposedDropdownMenuBoxExpanded,
        onExpandedChange = onExposedDropdownMenuBoxExpandedChange,
        modifier = modifier,
        content = {
            GetoOutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = textFieldModifier.menuAnchor(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                interactionSource = interactionSource,
                shape = shape,
                colors = colors,
            )

            if (showExposedDropdownMenu) {
                ExposedDropdownMenu(
                    expanded = exposedDropdownMenuExpanded,
                    onDismissRequest = onDismissRequest,
                    modifier = exposedDropdownMenuModifier,
                    scrollState = scrollState,
                    content = exposedDropdownMenuContent,
                )
            }
        },
    )
}
