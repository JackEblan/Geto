import com.android.build.gradle.LibraryExtension
import com.android.geto.configureAndroidCompose
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.com.android.geto.library.get().pluginId)
            }

            extensions.configure<LibraryExtension>() {
                configureAndroidCompose(this)
            }
        }
    }
}