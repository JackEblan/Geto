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

package com.android.geto.feature.applist

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.GetoAsyncImage
import com.android.geto.core.designsystem.theme.GetoTheme

@Composable
fun AppItem(
    modifier: Modifier = Modifier,
    icon: Drawable?,
    packageName: String,
    label: String,
    onItemClick: (String, String) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .testTag("applist:appItem")
        .clickable {
            onItemClick(
                packageName, label
            )
        }
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

        GetoAsyncImage(
            model = icon, contentDescription = null, modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label, style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = packageName, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun AppItemPreview() {
    GetoTheme {
        AppItem(icon = null,
                packageName = "packageName",
                label = "Label",
                onItemClick = { _, _ -> })
    }
}