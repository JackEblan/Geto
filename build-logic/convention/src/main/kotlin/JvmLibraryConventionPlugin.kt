import com.android.geto.configureKotlinJvm
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply(libs.plugins.com.android.geto.lint.get().pluginId)
            }
            configureKotlinJvm()
        }
    }
}