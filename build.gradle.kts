plugins {
    kotlin("jvm") version "2.2.0-RC"
    id("com.gradleup.shadow") version "8.3.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}


group = "ru.elementcraft"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.panda-lang.org/releases")
    maven("https://jitpack.io")

}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.projectkorra:projectkorra:1.12.0")
    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    shadow("com.zaxxer", "HikariCP", "6.2.1")
    shadow("org.mariadb.jdbc", "mariadb-java-client", "3.5.3")
    shadow("fr.mrmicky", "FastInv","3.1.1")
    shadow("dev.rollczi", "litecommands-bukkit", "3.9.7")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("org.projectlombok:lombok:1.18.38")
}

tasks.shadowJar {
    configurations = listOf(project.configurations.shadow.get())
    archiveClassifier = ""

}
tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.16")
    }
}

val targetJavaVersion = 16
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
