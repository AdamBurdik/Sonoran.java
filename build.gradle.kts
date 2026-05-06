plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.adamix.sonoran"
version = "2.1.1"

repositories {
    mavenCentral()
}

dependencies {
    api("org.slf4j:slf4j-api:2.0.9")

    implementation("com.google.code.gson:gson:2.14.0")
    implementation("com.google.guava:guava:33.5.0-jre")

    implementation("org.jetbrains:annotations:26.0.2")

    implementation("com.microsoft.signalr:signalr:11.0.0-preview.3.26207.106")

    // Lombok stuff
    compileOnly("org.projectlombok:lombok:1.18.46")
    annotationProcessor("org.projectlombok:lombok:1.18.46")

    testImplementation("org.slf4j:slf4j-simple:2.0.17")
    testCompileOnly("org.projectlombok:lombok:1.18.46")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.46")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.test {
    failOnNoDiscoveredTests = false
}