plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("application")
    kotlin("kapt") version "2.0.21"
}

application {
    mainClass.set("MainKt")
}

group = "it.polito"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20210307")

    implementation("io.ktor:ktor-server-core:2.0.0")
    implementation("io.ktor:ktor-server-netty:2.0.0")
    implementation("io.ktor:ktor-server-cors-jvm:2.0.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")

    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.0")

    implementation("com.google.dagger:dagger:2.46.1")
    kapt("com.google.dagger:dagger-compiler:2.46.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(19)
}