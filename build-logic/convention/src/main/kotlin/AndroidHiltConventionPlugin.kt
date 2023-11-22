import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("hilt.navigation.compose").get())
                "ksp"(libs.findLibrary("hilt.compiler").get())
                "ksp"(libs.findLibrary("dagger.compiler").get())
            }
        }
    }
}