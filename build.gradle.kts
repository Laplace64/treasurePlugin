plugins {
    kotlin("jvm") version "2.1.20-Beta1"
    id("com.gradleup.shadow") version "9.0.0-beta6"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "net.laplace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(kotlin("reflect", "2.0.20"))
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}


tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.runServer {
    minecraftVersion("1.21.1")
}
