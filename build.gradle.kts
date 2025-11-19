plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "be.sourcedbvba"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Google's Open Location Code library
    implementation("com.google.openlocationcode:openlocationcode:1.0.4")

    // Kotlin standard library
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("OpenLocationCodeDistanceKt")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}