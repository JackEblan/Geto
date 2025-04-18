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
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryJacoco)
    alias(libs.plugins.com.android.geto.hilt)
    alias(libs.plugins.com.android.geto.room)
}

android {
    namespace = "com.android.geto.data.room"

    sourceSets {
        getByName("androidTest").assets.srcDir("$projectDir/schemas")
    }
}

dependencies {
    implementation(projects.domain.model)
    implementation(projects.domain.repository)

    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(testFixtures(projects.common))
}