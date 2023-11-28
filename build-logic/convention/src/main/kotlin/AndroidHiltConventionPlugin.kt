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
                add("implementation", (libs.findLibrary("hilt.android").get()))
                add("implementation", (libs.findLibrary("androidx.hilt.navigation.compose").get()))
                add("ksp", (libs.findLibrary("hilt.compiler").get()))
                add("ksp", (libs.findLibrary("dagger.compiler").get()))

                add("kspAndroidTest", (libs.findLibrary("hilt.compiler").get()))
                add("ksp", (libs.findLibrary("hilt.compiler").get()))
            }
        }
    }
}