import com.android.build.gradle.LibraryExtension
import com.android.geto.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.geto.library")

            extensions.configure<LibraryExtension>() {
                configureAndroidCompose(this)
            }
        }
    }
}