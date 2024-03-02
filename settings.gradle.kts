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

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Geto"
include(":app")
include(":core:broadcast")
include(":core:clipboardmanager")
include(":core:clipboardmanager-test")
include(":core:common")
include(":core:database")
include(":core:data")
include(":core:data-test")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:packagemanager")
include(":core:packagemanager-test")
include(":core:securesettings")
include(":core:securesettings-test")
include(":core:shortcutmanager")
include(":core:shortcutmanager-test")
include(":core:testing")
include(":core:ui")

include(":feature:applist")
include(":feature:appsettings")

include(":lint")
