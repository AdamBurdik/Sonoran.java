plugins {
    id("java")
}

group = "me.adamix.sonoran.examples"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":"))
}