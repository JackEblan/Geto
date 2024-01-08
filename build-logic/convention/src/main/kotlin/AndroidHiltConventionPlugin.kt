import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.com.google.devtools.ksp.get().pluginId)
                apply(libs.plugins.com.google.dagger.hilt.android.get().pluginId)
            }

            dependencies {
                add("implementation", libs.hilt.android)
                add("implementation", libs.androidx.hilt.navigation.compose)
                add("ksp", libs.hilt.compiler)
                add("ksp", libs.dagger.compiler)

                add("kspAndroidTest", libs.hilt.compiler)
                add("ksp", libs.hilt.compiler)
            }
        }
    }
}