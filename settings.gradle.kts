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
include(":benchmarks")
include(":broadcast-receiver")
include(":common")
include(":data:datastore")
include(":data:repository")
include(":data:repository-test")
include(":data:room")
include(":design-system")
include(":domain:common")
include(":domain:foreground-service")
include(":domain:framework")
include(":domain:model")
include(":domain:repository")
include(":domain:use-case")
include(":feature:apps")
include(":feature:app-settings")
include(":feature:home")
include(":feature:service")
include(":feature:settings")
include(":feature:shizuku")
include(":framework:asset-manager")
include(":framework:clipboard-manager")
include(":framework:notification-manager")
include(":framework:package-manager")
include(":framework:secure-settings")
include(":framework:shizuku")
include(":framework:shortcut-manager")
include(":framework:usage-stats-manager")
include(":foreground-service")
include(":lint")
include(":roborazzi")