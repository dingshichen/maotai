plugins {
    `java-library`
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

