import com.android.build.api.dsl.ApplicationExtension
import com.android.geto.libs
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.gms.get().pluginId)
                apply(libs.plugins.firebase.crashlytics.get().pluginId)
            }

            dependencies {
                val bom = libs.firebase.bom
                add("implementation", platform(bom))
                "implementation"(libs.firebase.analytics)
                "implementation"(libs.firebase.crashlytics)
            }

            extensions.configure<ApplicationExtension> {
                buildTypes.configureEach {
                    // Disable the Crashlytics mapping file upload. This feature should only be
                    // enabled if a Firebase backend is available and configured in
                    // google-services.json.
                    configure<CrashlyticsExtension> {
                        mappingFileUploadEnabled = false
                    }
                }
            }
        }
    }
}