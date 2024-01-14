import com.android.build.gradle.LibraryExtension
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.com.android.geto.library.get().pluginId)
                apply(libs.plugins.com.android.geto.hilt.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "com.core.testing.HiltTestRunner"
                }
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.androidx.hilt.navigation.compose)
                add("implementation", libs.androidx.lifecycle.runtime.compose)
                add(
                    "implementation", libs.androidx.lifecycle.viewmodel.compose
                )
            }
        }
    }
}