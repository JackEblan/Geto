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

interface ClipboardManagerWrapper {

    /**
     * Sets the primary clip on the clipboard.
     *
     * Checks if the device's Android version is S_V2 or higher, returning true if it is, and false otherwise.
     *
     * @param label The label for the clip.
     * @param text The text to be copied to the clipboard.
     * @return `true` if the device's Android version is S_V2 or higher, `false` otherwise.
     */
    fun setPrimaryClip(label: String, text: String): Boolean
}