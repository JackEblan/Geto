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

package com.android.geto.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.geto.core.model.SecureSettings

@Composable
fun SecureSettingsItem(
    modifier: Modifier = Modifier,
    secureSettings: SecureSettings,
    onItemClick: (String) -> Unit,
) {
    Column(modifier = modifier
        .clickable { onItemClick(secureSettings.name!!) }
        .padding(10.dp)
        .fillMaxWidth()) {
        Text(
            text = secureSettings.name!!, style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = secureSettings.value ?: "", style = MaterialTheme.typography.bodySmall
        )
    }
}