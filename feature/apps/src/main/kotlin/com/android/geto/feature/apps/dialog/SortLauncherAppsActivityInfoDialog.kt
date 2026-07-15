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
package com.android.geto.feature.apps.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.designsystem.theme.supportsDynamicTheming
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.feature.apps.R
import com.android.geto.common.R as commonR

@Composable
internal fun SortLauncherAppsActivityInfoDialog(
    modifier: Modifier = Modifier,
    sortLauncherAppsActivityInfo: SortLauncherAppsActivityInfo,
    sortOrderLauncherAppsActivityInfo: SortOrderLauncherAppsActivityInfo,
    showSystem: Boolean,
    onDismissRequest: () -> Unit,
    onUpdateSortLauncherAppsActivityInfo: (SortLauncherAppsActivityInfo) -> Unit,
    onUpdateSortOrderLauncherAppsActivityInfo: (SortOrderLauncherAppsActivityInfo) -> Unit,
    onUpdateShowSystem: (Boolean) -> Unit,
) {
    var selectedSortLauncherAppsActivityInfoIndex by remember {
        mutableIntStateOf(
            SortLauncherAppsActivityInfo.entries.indexOf(sortLauncherAppsActivityInfo),
        )
    }

    var selectedSortOrderLauncherAppsActivityInfoIndex by remember {
        mutableIntStateOf(
            SortOrderLauncherAppsActivityInfo.entries.indexOf(sortOrderLauncherAppsActivityInfo),
        )
    }

    DialogContainer(
        modifier = modifier.verticalScroll(rememberScrollState()),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.sort),
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SortLauncherAppsActivityInfoDialogSelection(
                selectedSortLauncherAppsActivityInfoIndex = selectedSortLauncherAppsActivityInfoIndex,
                selectedSortOrderLauncherAppsActivityInfoIndex = selectedSortOrderLauncherAppsActivityInfoIndex,
                onUpdateSelectedSortLauncherAppsActivityInfoIndex = {
                    selectedSortLauncherAppsActivityInfoIndex = it
                },
                onUpdateSelectedSortOrderLauncherAppsActivityInfoIndex = {
                    selectedSortOrderLauncherAppsActivityInfoIndex = it
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            ShowSystemSetting(
                showSystem = showSystem,
                onUpdateShowSystem = onUpdateShowSystem,
            )

            SortLauncherAppsActivityInfoDialogButtons(
                onDismissRequest = onDismissRequest,
                selectedSortLauncherAppsActivityInfoIndex = selectedSortLauncherAppsActivityInfoIndex,
                selectedSortOrderLauncherAppsActivityInfoIndex = selectedSortOrderLauncherAppsActivityInfoIndex,
                onUpdateSortLauncherAppsActivityInfo = onUpdateSortLauncherAppsActivityInfo,
                onUpdateSortOrderLauncherAppsActivityInfo = onUpdateSortOrderLauncherAppsActivityInfo,
            )
        }
    }
}

@Composable
private fun SortLauncherAppsActivityInfoDialogSelection(
    selectedSortLauncherAppsActivityInfoIndex: Int,
    selectedSortOrderLauncherAppsActivityInfoIndex: Int,
    onUpdateSelectedSortLauncherAppsActivityInfoIndex: (Int) -> Unit,
    onUpdateSelectedSortOrderLauncherAppsActivityInfoIndex: (Int) -> Unit,
) {
    SingleChoiceSegmentedButtonRow {
        SortLauncherAppsActivityInfo.entries.forEachIndexed { index, sortLauncherAppsActivityInfo ->
            SegmentedButton(
                selected = selectedSortLauncherAppsActivityInfoIndex == index,
                onClick = { onUpdateSelectedSortLauncherAppsActivityInfoIndex(index) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = SortLauncherAppsActivityInfo.entries.size,
                ),
            ) {
                Text(text = sortLauncherAppsActivityInfo.getTitle())
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    SingleChoiceSegmentedButtonRow {
        SortOrderLauncherAppsActivityInfo.entries.forEachIndexed { index, sortOrderLauncherAppsActivityInfo ->
            SegmentedButton(
                selected = selectedSortOrderLauncherAppsActivityInfoIndex == index,
                onClick = { onUpdateSelectedSortOrderLauncherAppsActivityInfoIndex(index) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = SortOrderLauncherAppsActivityInfo.entries.size,
                ),
            ) {
                Text(text = sortOrderLauncherAppsActivityInfo.getTitle())
            }
        }
    }
}

@Composable
private fun SortLauncherAppsActivityInfoDialogButtons(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    selectedSortLauncherAppsActivityInfoIndex: Int,
    selectedSortOrderLauncherAppsActivityInfoIndex: Int,
    onUpdateSortLauncherAppsActivityInfo: (SortLauncherAppsActivityInfo) -> Unit,
    onUpdateSortOrderLauncherAppsActivityInfo: (SortOrderLauncherAppsActivityInfo) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onDismissRequest,
        ) {
            Text(text = stringResource(commonR.string.cancel))
        }
        TextButton(
            onClick = {
                val selectedSortLauncherAppsActivityInfo =
                    SortLauncherAppsActivityInfo.entries.getOrNull(
                        selectedSortLauncherAppsActivityInfoIndex,
                    )

                val selectedSortOrdeLauncherAppsActivityInfo =
                    SortOrderLauncherAppsActivityInfo.entries.getOrNull(
                        selectedSortOrderLauncherAppsActivityInfoIndex,
                    )

                selectedSortLauncherAppsActivityInfo?.let(
                    onUpdateSortLauncherAppsActivityInfo,
                )

                selectedSortOrdeLauncherAppsActivityInfo?.let(
                    onUpdateSortOrderLauncherAppsActivityInfo,
                )

                onDismissRequest()
            },
        ) {
            Text(text = stringResource(commonR.string.update))
        }
    }
}

@Composable
private fun ShowSystemSetting(
    modifier: Modifier = Modifier,
    showSystem: Boolean,
    onUpdateShowSystem: (Boolean) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = modifier
            .clickable {
                onUpdateShowSystem(!showSystem)
            }
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Show System",
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Show system applications",
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Switch(
            checked = showSystem,
            onCheckedChange = onUpdateShowSystem,
        )
    }
}

@Composable
private fun SortLauncherAppsActivityInfo.getTitle() = when (this) {
    SortLauncherAppsActivityInfo.Name -> stringResource(R.string.name)
    SortLauncherAppsActivityInfo.UpdateTime -> stringResource(R.string.update_time)
    SortLauncherAppsActivityInfo.InstallTime -> stringResource(R.string.install_time)
}

@Composable
private fun SortOrderLauncherAppsActivityInfo.getTitle() = when (this) {
    SortOrderLauncherAppsActivityInfo.Ascending -> stringResource(R.string.ascending)
    SortOrderLauncherAppsActivityInfo.Descending -> stringResource(R.string.descending)
}
