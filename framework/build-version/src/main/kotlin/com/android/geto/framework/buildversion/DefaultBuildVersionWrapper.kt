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
package com.android.geto.framework.buildversion

import android.os.Build
import javax.inject.Inject

internal class DefaultBuildVersionWrapper @Inject constructor() : BuildVersionWrapper {
    override fun getVersionCodeForSV2(): Int = Build.VERSION_CODES.S_V2

    override fun getSDKInt(): Int = Build.VERSION.SDK_INT
}
