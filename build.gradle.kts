plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.adamix.sonoran"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
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

sourceSets {
    create("examples") {
        java.srcDir("Examples")
        compileClasspath += sourceSets["main"].output + configurations["compileClasspath"]
        runtimeClasspath += output + compileClasspath + sourceSets["main"].runtimeClasspath
    }
}

val compileExamples by tasks.registering(JavaCompile::class) {
    source = sourceSets["examples"].java
    classpath = sourceSets["examples"].compileClasspath
    destinationDirectory.set(layout.buildDirectory.dir("classes/examples"))
}

val runExample by tasks.registering(JavaExec::class) {
    dependsOn(compileExamples)
    mainClass.set("me.adamix.sonoran.examples.Main")
    classpath = files(layout.buildDirectory.dir("classes/examples")) + sourceSets["main"].runtimeClasspath
}
