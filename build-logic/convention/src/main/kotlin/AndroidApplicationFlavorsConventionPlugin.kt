import com.android.build.api.dsl.ApplicationExtension
import com.android.geto.GetoFlavor
import com.android.geto.configureFlavors
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

class AndroidApplicationFlavorsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavors(this)
            }

            gradle.startParameter.taskNames.forEach { task ->
                if (task.contains(GetoFlavor.github.name.uppercaseFirstChar())) {
                    with(pluginManager) {
                        println("Applying Firebase plugins to Github Build")
                        apply(libs.plugins.com.android.geto.applicationFirebase.get().pluginId)
                    }
                }
            }
        }
    }
}