import com.android.build.api.dsl.LibraryExtension
import com.android.geto.configureKotlinAndroid
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
            }

            dependencies {
                "testImplementation"(libs.findLibrary("junit.junit").get())
                "androidTestImplementation"(libs.findLibrary("test.ext.junit").get())
                "androidTestImplementation"(libs.findLibrary("espresso.core").get())
            }
        }
    }
}