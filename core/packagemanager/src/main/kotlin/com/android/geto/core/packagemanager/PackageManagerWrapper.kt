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

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.android.geto.core.model.TargetApplicationInfo

interface PackageManagerWrapper {

    /**
     * Retrieves a list of all applications installed on the device.
     *
     * This method queries the Android PackageManager for a list of installed applications and maps each
     * application to a `TargetApplicationInfo` object, which contains simplified information about the application
     * suitable for use in the application's context.
     *
     * @return A list of `TargetApplicationInfo` objects representing the installed applications.
     */
    fun getInstalledApplications(): List<TargetApplicationInfo>

    /**
     * Retrieves the icon of a specific application.
     *
     * Given a package name, this method fetches the application's icon from the Android PackageManager.
     * If the package name does not correspond to an installed application, a `PackageManager.NameNotFoundException`
     * is thrown.
     *
     * @param packageName The package name of the application whose icon is to be retrieved.
     * @return A `Drawable` object representing the application's icon.
     * @throws PackageManager.NameNotFoundException if the application is not found.
     */
    fun getApplicationIcon(packageName: String): Drawable

    /**
     * Returns a "good" intent to launch a front-door activity in a package.
     * This is used, for example, to implement an "open" button when browsing
     * through packages.  The current implementation looks first for a main
     * activity in the category [Intent.CATEGORY_INFO], and next for a
     * main activity in the category [Intent.CATEGORY_LAUNCHER]. Returns
     * <code>null</code> if neither are found.
     *
     * @param packageName The name of the package to inspect.
     *
     * @return A fully-qualified Intent that can be used to launch the
     * main activity in the package. Returns <code>null</code> if the package
     * does not contain such an activity, or if <em>packageName</em> is not
     * recognized.
     */
    fun getLaunchIntentForPackage(packageName: String): Intent?

}