import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

group = "me.cirosanchez.react"
version = "0.0.1"

plugins {
    id ("com.github.johnrengelman.shadow") version "8.1.1"
    id ("org.jetbrains.kotlin.jvm") version "1.9.21"
}

repositories {
    mavenCentral()
    maven (
        url = "https://papermc.io/repo/repository/maven-public/"
    )
    maven (
        url = "https://jitpack.io"
    )
    maven (
        url = "https://repo.extendedclip.com/content/repositories/placeholderapi/"
    )
}

dependencies {
    // Paper and Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0-Beta2")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    // Commands
    implementation("com.github.Revxrsal.Lamp:common:3.1.8")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.1.8")

    // Adventure
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")

    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.11.5")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> { // Preserve parameter names in the bytecode
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinJvmCompile> { // optional: if you're using Kotlin
    compilerOptions {
        javaParameters = true
    }
}

tasks {
    shadowJar {
        minimize()
    }
}