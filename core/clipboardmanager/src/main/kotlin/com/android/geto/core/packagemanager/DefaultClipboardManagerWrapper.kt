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
package com.android.geto.core.packagemanager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultClipboardManagerWrapper @Inject constructor(
    @ApplicationContext private val context: Context,
) : ClipboardManagerWrapper {

    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
    override val atLeastApi32 =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2

    override fun setPrimaryClip(label: String, text: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))
    }
}
