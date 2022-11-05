import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}
val jdkVersion = "11"
val fileVersion = file("project.version").readText().trim()
allprojects {
    group = "llh4github.jimmer"
    version = fileVersion
}

subprojects {

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = jdkVersion
    }
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
    }
    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()
    }

}