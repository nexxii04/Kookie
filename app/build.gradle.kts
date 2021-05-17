import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.0"

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    id("com.gorylenko.gradle-git-properties") version "2.3.1"
}

group = "be.zvz"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven {
        name = "powernukkit-releases"
        url = uri("https://api.bintray.com/maven/powernukkit/powernukkit/PowerNukkit")
    }
    maven {
        name = "powernukkit-snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-stdlib-jdk8")

    // Kotlin coroutines
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.5.0-native-mt")

    // jline (console)
    implementation(group = "org.jline", name = "jline", version = "3.20.0")
    implementation(group = "net.minecrell", name = "terminalconsoleappender", version = "1.2.0")

    // Jackson
    implementation(group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-yaml", version = "2.12.3")
    implementation(group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-properties", version = "2.12.3")

    // Apache commons IO
    implementation(group = "commons-io", name = "commons-io", version = "2.8.0")

    // ini4j
    implementation(group = "org.ini4j", name = "ini4j", version = "0.5.4")

    // jna
    implementation(group = "net.java.dev.jna", name = "jna", version = "5.8.0")

    // Logger
    implementation(group = "org.slf4j", name = "slf4j-api", version = "1.7.30")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
    implementation(group = "org.fusesource.jansi", name = "jansi", version = "2.3.2")

    // netty
    implementation(group = "io.netty", name = "netty-all", version = "4.1.63.Final")

    // bedrock-network
    implementation(group = "org.powernukkit.bedrock.network", name = "raknet", version = "1.6.25-PN")

    // koloboke
    implementation(group = "com.koloboke", name = "koloboke-impl-common-jdk8", version = "1.0.0")
    implementation(group = "com.koloboke", name = "koloboke-impl-jdk8", version = "1.0.0")
    implementation(group = "com.koloboke", name = "koloboke-api-jdk8", version = "1.0.0")

    // Use the Kotlin test library.
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = java.sourceCompatibility.toString()
    }
}

application {
    // Define the main class for the application.
    mainClass.set("be.zvz.kookie.AppKt")
}