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

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.ui.TargetApplicationInfoPreviewParameterData.targetApplicationInfoLists

class TargetApplicationInfoPreviewParameterProvider :
    PreviewParameterProvider<List<TargetApplicationInfo>> {

    override val values: Sequence<List<TargetApplicationInfo>> =
        sequenceOf(targetApplicationInfoLists)
}

object TargetApplicationInfoPreviewParameterData {
    val targetApplicationInfoLists = List(5) { index ->
        TargetApplicationInfo(
            flags = 0, packageName = "packageName$index", label = "Label $index"
        )
    }
}