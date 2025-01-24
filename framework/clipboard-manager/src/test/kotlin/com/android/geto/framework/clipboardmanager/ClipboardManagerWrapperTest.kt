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
package com.android.geto.framework.clipboardmanager

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class ClipboardManagerWrapperTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var clipboardManagerWrapper: AndroidClipboardManagerWrapper

    @BeforeTest
    fun setUp() {
        clipboardManagerWrapper = AndroidClipboardManagerWrapper(context = context)
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.S])
    fun setPrimaryClip_onApi32() = runTest {
        assertTrue(clipboardManagerWrapper.setPrimaryClip(label = "label", text = "text"))
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.TIRAMISU])
    fun setPrimaryClip_onApi33() = runTest {
        assertFalse(clipboardManagerWrapper.setPrimaryClip(label = "label", text = "text"))
    }
}
