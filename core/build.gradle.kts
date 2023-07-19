fun properties(key: String) = project.findProperty(key).toString()

plugins {
    `java-library`
    `maven-publish`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    testImplementation("junit:junit:4.12")

    implementation("com.google.code.findbugs:annotations:3.0.1u2")
    implementation("org.antlr:antlr4-runtime:4.12.0")
    implementation("cn.hutool:hutool-all:5.8.10")
}

tasks.test {
    useJUnit()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = properties("app.group")
            artifactId = "core"
            version = properties("app.version")
            from(components["java"])
        }
    }
}

