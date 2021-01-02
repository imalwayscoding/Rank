plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.github.imalwayscoding"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io/")
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")

    compileOnly("com.destroystokyo.paper:paper-api:1.16.4-R0.1-SNAPSHOT")
    implementation("com.github.noonmaru:kommand:0.6.3")
}

tasks {
    shadowJar {
        archiveBaseName.set(project.property("pluginName").toString())
        archiveVersion.set("")
        archiveClassifier.set("")
    }
}
