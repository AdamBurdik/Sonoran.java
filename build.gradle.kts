plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.adamix.sonoran"
version = "1.5.3"

repositories {
    mavenCentral()
}

dependencies {
    api("org.slf4j:slf4j-api:2.0.9")

    implementation("com.google.code.gson:gson:2.13.1")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.5")

    implementation("org.jetbrains:annotations:26.0.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
