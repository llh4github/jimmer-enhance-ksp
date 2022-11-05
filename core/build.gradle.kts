plugins {
    kotlin("jvm") version "1.7.10"
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}
val generateSources = tasks.register<Jar>("generateSources") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}
val generateJavadoc = tasks.register<Jar>("generateJavadoc") {
    archiveClassifier.set("javadoc")
}
publishing {
    publications {
        create<MavenPublication>("dist") {
            group = project.group
//            artifactId = project.name
            version = project.version.toString()
            artifactId = "core"
            artifact(generateSources)
            artifact(generateJavadoc)
            version = project.version.toString()
            from(components["java"])
            pom {
                name.set("${project.group}:core")
            }
        }
    }
}
dependencies {
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)

    implementation(libs.ktfmt)
    implementation(libs.googleKsp)
    implementation(libs.jimmer.sql)
    implementation(libs.jimmer.kotlin)
    implementation(libs.jimmer.ksp)
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.engine)
}
tasks.test {
    useJUnitPlatform()
}