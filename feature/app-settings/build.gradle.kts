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

plugins {
    alias(libs.plugins.com.android.geto.feature)
    alias(libs.plugins.com.android.geto.libraryCompose)
    alias(libs.plugins.com.android.geto.libraryJacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.android.geto.feature.appsettings"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)

    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.robolectric)
    testImplementation(projects.core.testing)
    testImplementation(projects.core.screenshotTesting)
    testImplementation(libs.roborazzi)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(projects.core.testing)
}