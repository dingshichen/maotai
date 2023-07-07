import org.jetbrains.compose.desktop.application.dsl.TargetFormat

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":core"))
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = properties("app.name")
            packageVersion = version.toString()
        }
    }
}

tasks {
    withType<ProcessResources> {
        from("${project.rootDir}/gradle.properties")
        into("${project.projectDir}/src/jvmMain/resources")
    }
}