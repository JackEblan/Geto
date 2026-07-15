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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.feature.apps.R
import com.android.geto.common.R as commonR

@Composable
internal fun SortLauncherAppsActivityInfoDialog(
    modifier: Modifier = Modifier,
    sortLauncherAppsActivityInfo: SortLauncherAppsActivityInfo,
    sortOrderLauncherAppsActivityInfo: SortOrderLauncherAppsActivityInfo,
    onDismissRequest: () -> Unit,
    onUpdateSortLauncherAppsActivityInfo: (SortLauncherAppsActivityInfo) -> Unit,
    onUpdateSortOrderLauncherAppsActivityInfo: (SortOrderLauncherAppsActivityInfo) -> Unit,
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

            SingleChoiceSegmentedButtonRow {
                SortLauncherAppsActivityInfo.entries.forEachIndexed { index, sortLauncherAppsActivityInfo ->
                    SegmentedButton(
                        selected = selectedSortLauncherAppsActivityInfoIndex == index,
                        onClick = { selectedSortLauncherAppsActivityInfoIndex = index },
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

            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.sort),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SingleChoiceSegmentedButtonRow {
                SortOrderLauncherAppsActivityInfo.entries.forEachIndexed { index, sortOrderLauncherAppsActivityInfo ->
                    SegmentedButton(
                        selected = selectedSortOrderLauncherAppsActivityInfoIndex == index,
                        onClick = { selectedSortOrderLauncherAppsActivityInfoIndex = index },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = SortLauncherAppsActivityInfo.entries.size,
                        ),
                    ) {
                        Text(text = sortOrderLauncherAppsActivityInfo.getTitle())
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
