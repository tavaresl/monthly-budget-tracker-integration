plugins {
    application
    kotlin("jvm") version "1.9.23"
}

group = "com.lukaorocha"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "com.typesafe", name="config", version="1.4.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
    implementation("com.fasterxml.jackson.core:jackson-core:2.16.+")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.16.+")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.+")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.lukaorocha.MainKt")
}
