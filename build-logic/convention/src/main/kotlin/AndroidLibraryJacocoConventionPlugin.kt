import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.geto.configureJacoco
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("jacoco")
                apply(libs.plugins.com.android.library.get().pluginId)
            }
            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }

}