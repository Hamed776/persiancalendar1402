import org.gradle.kotlin.dsl.libs

// Doesn't work without this workaround: https://github.com/gradle/gradle/issues/15383
// See settings.gradle.kts of this folder also.
val Project.libs: org.gradle.accessors.dm.LibrariesForLibs get() = extensions.getByType()

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.myket.ir") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinx.serialization.json)
   // implementation(mapOf("name" to "multidex-2.0.1", "ext" to "aar"))
    implementation(libs.multidex)
}

gradlePlugin {
    plugins {
        create("dependencies") {
            id = "io.github.persiancalendar.appbuildplugin"
            implementationClass = "io.github.persiancalendar.gradle.AppBuildPlugin"
        }
    }
}
