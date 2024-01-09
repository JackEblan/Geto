import com.android.geto.configureKotlinJvm
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            with(pluginManager) {
                apply(libs.plugins.org.jetbrains.kotlin.jvm.get().pluginId)
            }
            configureKotlinJvm()
        }
    }
}