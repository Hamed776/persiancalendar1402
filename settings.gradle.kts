pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
//        flatDir {
//            dirs("libs")
//        }
        maven { url = uri("https://maven.myket.ir") }
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

//        flatDir {
//            dirs("libs")
//        }

        maven { url = uri("https://maven.myket.ir") }
        maven { url = uri("https://jitpack.io") }

    }
}
rootProject.name = "persian-calendar"
include(":PersianCalendar")
includeBuild("gradlePlugins")
