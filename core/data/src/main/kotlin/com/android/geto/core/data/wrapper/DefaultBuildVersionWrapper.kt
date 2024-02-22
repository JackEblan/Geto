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

package com.android.geto.core.data.wrapper

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import com.android.geto.core.domain.wrapper.BuildVersionWrapper
import javax.inject.Inject

class DefaultBuildVersionWrapper @Inject constructor() : BuildVersionWrapper {
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    override fun isApi32Higher(): Boolean {
        return Build.VERSION.SDK_INT >= 32
    }
}