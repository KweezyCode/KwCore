import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("com.gradleup.shadow") version "8.3.6"
}

group = "com.kweezy"
version = "1.0-SNAPSHOT"

val minecraft_version = "1_5_R3"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":craftbukkit"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "org.bukkit.craftbukkit.Main"
        }
        exclude("org/bouncycastle/**")
    }
    withType<ShadowJar> {
        relocate("org.bouncycastle", "net.minecraft.v${minecraft_version}.org.bouncycastle")
        relocate("joptsimple", "org.bukkit.craftbukkit.libs.joptsimple")
        relocate("jline", "org.bukkit.craftbukkit.libs.jline")
        relocate("org.ibex", "org.bukkit.craftbukkit.libs.org.ibex")
        relocate("org.gjt", "org.bukkit.craftbukkit.libs.org.gjt")
        relocate("com.google.gson", "org.bukkit.craftbukkit.libs.com.google.gson")
        relocate("org.bukkit.craftbukkit", "org.bukkit.craftbukkit.v${minecraft_version}") {
            exclude("org.bukkit.craftbukkit.Main*")
        }
        relocate("net.minecraft.server", "net.minecraft.server.v${minecraft_version}")
        minimize()
    }
}