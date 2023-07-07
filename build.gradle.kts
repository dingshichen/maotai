fun properties(key: String) = project.findProperty(key).toString()

allprojects {
    group = properties("app.group")
    version = properties("app.version")

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

