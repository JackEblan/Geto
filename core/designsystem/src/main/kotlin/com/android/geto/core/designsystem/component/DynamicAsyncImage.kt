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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.android.geto.core.designsystem.R

@Composable
fun DynamicAsyncImage(
    model: Any?, contentDescription: String?, modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.ic_launcher_round),
) {
    var isError by remember { mutableStateOf(false) }

    val imageLoader =
        rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current).data(model)
            .size(Size.ORIGINAL).build(),
                                  onState = { state ->
                                      isError = state is AsyncImagePainter.State.Error
                                  })

    Image(
        painter = if (isError.not()) imageLoader else placeholder,
        contentDescription = contentDescription, modifier = modifier.size(50.dp)
    )
}